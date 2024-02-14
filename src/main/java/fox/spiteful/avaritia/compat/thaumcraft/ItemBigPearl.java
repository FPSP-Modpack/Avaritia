package fox.spiteful.avaritia.compat.thaumcraft;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import fox.spiteful.avaritia.Avaritia;

public class ItemBigPearl extends Item {

    public ItemBigPearl() {
        setMaxStackSize(1);
        this.setUnlocalizedName("extremely_primordial_pearl");
        this.setTextureName("avaritia:big_pearl");
        setCreativeTab(Avaritia.tab);
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.epic;
    }

    @Override
    public boolean hasContainerItem() {
        return true;
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack) {
        return false;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return itemStack;
    }

}
