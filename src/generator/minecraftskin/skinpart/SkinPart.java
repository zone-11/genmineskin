package generator.minecraftskin.skinpart;

public enum SkinPart {
	HEAD(new int[] {0, 0, 32, 16}),
	BODY(new int[] {16, 16, 24, 16}),
	HAND1(new int[] {40, 16, 16, 16}),
	HAND2(new int[] {32, 48, 16, 16}),
	LEG1(new int[] {0, 16, 16, 16}),
	LEG2(new int[] {16, 48, 16, 16});
	
	private int[] coords;
	private SkinPart(int[] coords) {
		this.coords = coords;
	}
	public int[] getCoords() { return coords; }
}
