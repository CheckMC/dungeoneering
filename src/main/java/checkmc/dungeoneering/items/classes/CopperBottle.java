package checkmc.dungeoneering.items.classes;

import checkmc.dungeoneering.DungeoneeringMain;
import net.minecraft.component.DataComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.ArrayList;

public class CopperBottle extends Item {
    private int maxChainLength;

    public CopperBottle(Settings settings) {
        super(settings);
    }

    public CopperBottle(Settings settings, int maxChainLength) {
        super(settings);
        this.maxChainLength = maxChainLength;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        int maxDamage = itemStack.get(DataComponentTypes.MAX_DAMAGE);
        int damageTaken = itemStack.get(DataComponentTypes.DAMAGE);
        int currentDurability = maxDamage - damageTaken;
        if (currentDurability == 0) {
            // no lightning left
            return TypedActionResult.fail(itemStack);
        }
        else {
            ArrayList<Entity> excludeList = new ArrayList<Entity>();
            excludeList.add(user);
            strikeNextEntity(user, maxChainLength, world, user, excludeList);
            itemStack.set(DataComponentTypes.DAMAGE, damageTaken + 1);
            return TypedActionResult.success(itemStack, true);
        }
    }

    // Recursive method to find the next entity in the lightning strike chain
    private void strikeNextEntity(Entity from, int numLeft, World world, PlayerEntity user, ArrayList<Entity> exclude) {
        DungeoneeringMain.LOGGER.info("Running strike next entity, numleft = "+numLeft);
        int area = 10;
        if (numLeft == 0) {
            return;
        }

        Box searchArea = from.getBoundingBox().expand(area);
        ArrayList<Entity> allNearby = new ArrayList<>(from.getWorld().getNonSpectatingEntities(LivingEntity.class, searchArea));
        Entity nearest = null;
        allNearby.removeAll(exclude);
        if (!allNearby.isEmpty()) {
            nearest = allNearby.getFirst();
        } else {
            return;
        }

        // Finding nearest mob that is valid
        for (int i = 0; i < allNearby.size(); i++) {
            Entity current = allNearby.get(i);
            if (current.distanceTo(from) < nearest.distanceTo(from)) {
                RaycastContext raycastContext = new RaycastContext(from.getPos(), current.getPos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, from);
                HitResult raycastResult = world.raycast(raycastContext);
                if (validNextMob(from, current, world)) {
                    nearest = current;
                    //DungeoneeringMain.LOGGER.info("FOUND VALID");
                }
            }
        }

        //found nearest, check for validity
        if (!validNextMob(from, nearest, world)) {
            DungeoneeringMain.LOGGER.info("Nearest not valid");
            return;
        }

        strikeMob(nearest, world);
        exclude.add(nearest);
        strikeNextEntity(nearest, numLeft-1, world, user, exclude);
    }

    private boolean validNextMob(Entity entityFrom, Entity entityCheck, World world) {
        if (entityCheck instanceof PlayerEntity) {
            return false;
        }
        RaycastContext raycastContext = new RaycastContext(entityFrom.getPos().add(0,1,0), entityCheck.getPos().add(0,1,0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entityFrom);
        HitResult raycastResult = world.raycast(raycastContext);
        DungeoneeringMain.LOGGER.info(raycastResult.toString());

        if (raycastResult.getType() != HitResult.Type.BLOCK) {
            DungeoneeringMain.LOGGER.info("Found valid mob");
            return true;
        }

        DungeoneeringMain.LOGGER.info("Invalid mob");
        return false;
    }

    private boolean strikeMob(Entity toStrike, World world) {
        DungeoneeringMain.LOGGER.info("STRIKING!");
        LivingEntity aliveEntity = (LivingEntity) toStrike;
        aliveEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 50));
        return true;
    }
}
