package net.mcreator.jigendanretsu.item;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.Item;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBase;

import net.mcreator.jigendanretsu.ElementsJigenDanretsu;

@ElementsJigenDanretsu.ModElement.Tag
public class ItemJigenDanretsu extends ElementsJigenDanretsu.ModElement {
	@GameRegistry.ObjectHolder("jigendanretsu:jigendanretsuhelmet")
	public static final Item helmet = null;
	@GameRegistry.ObjectHolder("jigendanretsu:jigendanretsubody")
	public static final Item body = null;
	@GameRegistry.ObjectHolder("jigendanretsu:jigendanretsulegs")
	public static final Item legs = null;
	@GameRegistry.ObjectHolder("jigendanretsu:jigendanretsuboots")
	public static final Item boots = null;
	public ItemJigenDanretsu(ElementsJigenDanretsu instance) {
		super(instance, 2);
	}

	@Override
	public void initElements() {
		ItemArmor.ArmorMaterial enuma = EnumHelper.addArmorMaterial("JIGENDANRETSU", "jigendanretsu:uwuarmor", 25, new int[]{2, 5, 6, 10}, 9,
				(net.minecraft.util.SoundEvent) net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("")), 0f);
		elements.items.add(() -> new ItemArmor(enuma, 0, EntityEquipmentSlot.HEAD) {
			@Override
			@SideOnly(Side.CLIENT)
			public ModelBiped getArmorModel(EntityLivingBase living, ItemStack stack, EntityEquipmentSlot slot, ModelBiped defaultModel) {
				ModelBiped armorModel = new ModelBiped();
				armorModel.bipedHead = new ModelEyeSnug().Head;
				armorModel.isSneak = living.isSneaking();
				armorModel.isRiding = living.isRiding();
				armorModel.isChild = living.isChild();
				return armorModel;
			}

			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
				return "jigendanretsu:textures/jigen_danretsu.png";
			}
		}.setUnlocalizedName("jigendanretsuhelmet").setRegistryName("jigendanretsuhelmet").setCreativeTab(CreativeTabs.COMBAT));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(helmet, 0, new ModelResourceLocation("jigendanretsu:jigendanretsuhelmet", "inventory"));
	}
	public static class ModelEyeSnug extends ModelBase {
		private final ModelRenderer Head;
		public ModelEyeSnug() {
			this.textureWidth = 64;
			this.textureHeight = 16;
			this.Head = new ModelRenderer(this);
			this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
			this.Head.cubeList.add(new ModelBox(this.Head, 8, 8, -4.0F, -8.0F, -4.1F, 8, 8, 0, 0.0F, false));
			this.Head.cubeList.add(new ModelBox(this.Head, 24, 0, -4.0F, -8.0F, -4.2F, 8, 8, 0, 0.0F, false));
		}

		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			this.Head.render(f5);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
			super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
		}
	}
}