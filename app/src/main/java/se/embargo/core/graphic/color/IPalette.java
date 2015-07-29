package se.embargo.core.graphic.color;

public interface IPalette {
	/**
	 * Maps an image color to a palette color
	 * @param	r1	Red component
	 * @param	g1	Green component
	 * @param	b1	Blue component
	 * @return		The nearest color
	 */
	public int getNearestColor(final int r1, final int g1, final int b1);
	
	/**
	 * @return	The number of colors in this palette.
	 */
	public int getColorCount();
}
