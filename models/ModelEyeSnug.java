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