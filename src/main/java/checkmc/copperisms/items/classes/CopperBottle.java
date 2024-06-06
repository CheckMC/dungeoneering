package checkmc.copperisms.items.classes;

import checkmc.copperisms.CopperismsMain;
import checkmc.copperisms.items.CopperismsItems;
import net.minecraft.component.DataComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.tick.Tick;

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
    public void inventoryTick( ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int ticksLeft = stack.get(CopperismsItems.TICKS_LEFT);
        if (ticksLeft > 0) {
            stack.set(CopperismsItems.TICKS_LEFT, ticksLeft-1);
        }
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
        CopperismsMain.LOGGER.info("Running strike next entity, numleft = "+numLeft);
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
                if (validNextMob(from, current, world)) {
                    nearest = current;
                    //CopperismsMain.LOGGER.info("FOUND VALID");
                }
            }
        }

        //found nearest, check for validity
        if (!validNextMob(from, nearest, world)) {
            CopperismsMain.LOGGER.info("Nearest not valid");
            return;
        }

        strikeMob(nearest, world, this.getDefaultStack());
        exclude.add(nearest);
        strikeNextEntity(nearest, numLeft-1, world, user, exclude);
    }

    private boolean validNextMob(Entity entityFrom, Entity entityCheck, World world) {
        if (entityCheck instanceof PlayerEntity) {
            return false;
        }
        RaycastContext raycastContext = new RaycastContext(entityFrom.getPos().add(0,1,0), entityCheck.getPos().add(0,1,0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entityFrom);
        HitResult raycastResult = world.raycast(raycastContext);
        CopperismsMain.LOGGER.info(raycastResult.toString());

        if (raycastResult.getType() != HitResult.Type.BLOCK) {
            CopperismsMain.LOGGER.info("Found valid mob");
            return true;
        }

        CopperismsMain.LOGGER.info("Invalid mob");
        return false;
    }

    private boolean strikeMob(Entity toStrike, World world, ItemStack stack) {
        stack.set(CopperismsItems.TICKS_LEFT, 3);
        CopperismsMain.LOGGER.info("STRIKING!");
        LivingEntity aliveEntity = (LivingEntity) toStrike;
        aliveEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 50));
        aliveEntity.damage(world.getDamageSources().lightningBolt(), 5f);
        return true;
    }


}
