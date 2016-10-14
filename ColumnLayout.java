import java.awt.*;

/**
 * A layout manager similar to java.awt.GridLayout, but which makes resizing of 
 * the child components optional.
 * <p>
 * Children are added to successive columns before moving to the next row.<br> 
 * Children have their preferred size, or can optionally be made as wide as 
 * their column and/or as high as their row.<br>
 * There is an option to ignore invisible children in the layout. Otherwise,
 * a space will be left for them.
 * <p>
 * If the number of columns is specified to be <= 0, the ColumnLayout 
 * will layout its children in 1 column.<br>
 * If negative gaps are specified, overlapping of children will occur.
 * <p>
 * @author Bill Conlen
 */
public class ColumnLayout implements LayoutManager {

   private int rows, cols;
   private int hGap;
   private int vGap;
   private int uniformSizing;
   private boolean skipInvisible;

   private int[] widths, heights;

   /**
    * A uniformSizing option which indicates that children should have 
    * their preferred size.
    */
   public static final int NEITHER = 0;

   /**
    * A uniformSizing option which indicates that children should be 
    * made as wide as their column.
    */
   public static final int WIDTH = 1;

   /**
    * A uniformSizing option which indicates that children should be 
    * made as high as their row.
    */
   public static final int HEIGHT = 2;

   /**
    * A uniformSizing option which indicates that children should be 
    * made as wide as their column and as high as their row.
    */
   public static final int BOTH = 3;

   private final int PREFERRED = 0;
   private final int MINIMUM = 1;

   /**
    * Creates a default ColumnLayout with one column.
    * <p>
    * Gaps will be 0; uniformSizing will be NEITHER and 
    * skipInvisible will be true.
    */
   public ColumnLayout() {
      this(1, 0, 0, NEITHER, true);
   }

   /**
    * Creates a ColumnLayout with the specified columns.
    * <p>
    * Gaps will be 0; uniformSizing will be NEITHER and 
    * skipInvisible will be true.
    * <p>
    * @param cols the number of columns
    */
   public ColumnLayout(int cols) {
      this(cols, 0, 0, NEITHER, true);
   }

   /**
    * Creates a ColumnLayout with the specified columns and gaps.
    * <p>
    * uniformSizing will be NEITHER and skipInvisible will be true.
    * <p>
    * @param cols the number of columns
    * @param hGap the horizontal gap
    * @param vGap the vertical gap
    */
   public ColumnLayout(int cols, int hGap, int vGap) {
      this(cols, hGap, vGap, NEITHER, true);
   }

   /**
    * Creates a ColumnLayout with the specified columns, gaps, and 
    * uniformSizing option.
    * <p>
    * skipInvisible will be true.
    * <p>
    * @param cols the number of columns
    * @param hGap the horizontal gap
    * @param vGap the vertical gap
    * @param uniformSizing the uniformSizing option
    */
   public ColumnLayout(int cols, int hGap, int vGap, int uniformSizing) {
      this(cols, hGap, vGap, uniformSizing, true);
   }

   /**
    * Creates a ColumnLayout with the specified columns, gaps, 
    * uniformSizing option, and skipInvisible flag.
    * <p>
    * @param cols the number of columns
    * @param hGap the horizontal gap
    * @param vGap the vertical gap
    * @param uniformSizing the uniformSizing option
    * @param skipInvisible true if invisible children should be ignored in
    * the layout
    */
   public ColumnLayout(int cols, int hGap, int vGap, int uniformSizing,
                       boolean skipInvisible) {
      if (cols <= 0)
         cols = 1;
      this.cols = cols;
      this.hGap = hGap;
      this.vGap = vGap;
      if (uniformSizing == NEITHER || uniformSizing == WIDTH ||
          uniformSizing == HEIGHT  || uniformSizing == BOTH)
         this.uniformSizing = uniformSizing;
      else
         this.uniformSizing = NEITHER;
      this.skipInvisible = skipInvisible;
   }

   /**
    * Determines the number of rows, the width of each column,
    * and the height of each row.
    * <p>
    * (not intended to be called by application programs)
    * <p>
    * @param cont the container which is being laid out 
    * @param nComponents the number of components in the container
    * @param type the type of layout being considered: PREFERRED or MINIMUM
    */
   protected void configureGrid (Container cont, int nComponents, int type) {

      // determine number of rows
      rows = nComponents / cols;
      if (nComponents % cols > 0)
         rows++;

      // determine width of each column and height of each row based on the
      // dimensions of the children in that column or row
      widths  = new int[cols];
      heights = new int[rows];
      for (int i=0; i<cols; i++)
         widths[i] = 0;
      for (int i=0; i<rows; i++)
         heights[i] = 0;
      int j = 0; // needed because some components are skipped
      for (int i=0; i<nComponents; i++) {
         int col = j % cols;
         int row = j / cols;
         Dimension size;
         // skip invisible components
         if (skipInvisible && !cont.getComponent(i).isVisible())
            continue;
         if (type == MINIMUM)
            size = cont.getComponent(i).getMinimumSize();
         else {
            size = cont.getComponent(i).getPreferredSize();
         }
         widths[col]  = Math.max (widths[col],  size.width);
         heights[row] = Math.max (heights[row], size.height);
         j++;
      }
   }

   /** 
    * Determines the preferred size of the specified container using this 
    * layout.
    * <p>
    * @param cont the container which is being laid out 
    * @return the preferred size of the specified container
    * @see #minimumLayoutSize
    */
   public Dimension preferredLayoutSize(Container cont) {
      Insets insets = cont.getInsets();
      int nComponents = cont.getComponentCount();

      configureGrid (cont, nComponents, PREFERRED);

      // total width without gaps
      int w = 0;
      for (int i=0; i<cols; i++)
         w += widths[i];

      // total height without gaps
      int h = 0;
      for (int i=0; i<rows; i++)
         h += heights[i];

      return new Dimension(insets.left + insets.right + w + (cols-1)*hGap, 
                           insets.top + insets.bottom + h + (rows-1)*vGap);
   }

   /**
    * Determines the minimum size of the specified container using this layout.
    * <p>
    * @param cont the container which is being laid out 
    * @return the minimum size of the specified container
    * @see #preferredLayoutSize
    */
   public Dimension minimumLayoutSize(Container cont) {
      Insets insets = cont.getInsets();
      int nComponents = cont.getComponentCount();

      // not the same as preferredLayoutSize because based on the minimum
      // size of the container's children
      configureGrid (cont, nComponents, MINIMUM);

      // total width without gaps
      int w = 0;
      for (int i=0; i<cols; i++)
         w += widths[i];

      // total height without gaps
      int h = 0;
      for (int i=0; i<rows; i++)
         h += heights[i];

      return new Dimension(insets.left + insets.right + w + (cols-1)*hGap, 
                           insets.top + insets.bottom + h + (rows-1)*vGap);
   }

   /** 
    * Lays out the specified container using this layout.
    * <p>
    * Most applications do not call this method directly. This method is 
    * called when a container calls its layout method.
    * <p>
    * @param cont the container which is being laid out 
    * @see Container
    */
   public void layoutContainer(Container cont) {
      Insets insets = cont.getInsets();
      int nComponents = cont.getComponentCount();
      if (nComponents == 0)
          return;
      configureGrid (cont, nComponents, PREFERRED);

      // place components
      int i = 0;
      for (int r=0,y=insets.top; r<rows; y+=heights[r++]+vGap) {    // by rows
         for (int c=0,x=insets.left; c<cols; x+=widths[c++]+hGap) { // by cols
            Component comp = null;

            // get the next component (possibly skipping invisible ones)
            for (;i<nComponents; i++) {
               comp = cont.getComponent(i);
               // break if this is one we should include
               if (comp.isVisible() || !skipInvisible)
                  break;
            }

            // place next component
            if (i < nComponents) {

               // determine width
               int width;
               if (uniformSizing == WIDTH || uniformSizing == BOTH) 
                  width = widths[c];
               else 
                  width = comp.getPreferredSize().width;

               // determine height
               int height;
               if (uniformSizing == HEIGHT || uniformSizing == BOTH) 
                  height = heights[r];
               else 
                  height = comp.getPreferredSize().height;

               comp.setBounds(x, y, width, height);
               i++;
            }
         }
      }
   }
    
   /**
    * Gets the number of columns.
    * <p>
    * @return the number of columns
    */
   public int getCols() {
      return cols;
   }

   /**
    * Sets the number of columns.
    * <p>
    * If the number of columns is specified to be <= 0, a 1-column ColumnLayout
    * will be generated.
    * <p>
    * @param cols the number of columns
    */
   public void setCols(int cols) {
      if (cols <= 0)
         cols = 1;
      this.cols = cols;
   }

   /**
    * Gets the horizontal gap.
    * <p>
    * @return the horizontal gap
    */
   public int getHorizontalGap() {
      return hGap;
   }

   /**
    * Sets the horizontal gap.
    * <p>
    * If a negative gap is specified, overlapping of children will occur.
    * <p>
    * @param hGap the horizontal gap
    */
   public void setHorizontalGap(int hGap) {
      this.hGap = hGap;
   }

   /**
    * Gets the vertical gap.
    * <p>
    * @return the vertical gap
    */
   public int getVerticalGap() {
      return vGap;
   }

   /**
    * Sets the vertical gap.
    * <p>
    * If a negative gap is specified, overlapping of children will occur.
    * <p>
    * @param vGap the vertical gap
    */
   public void setVerticalGap(int vGap) {
      this.vGap = vGap;
   }

   /**
    * Gets the uniformSizing option.
    * <p>
    * @return the uniformSizing option
    */
   public int getUniformSizing() {
      return uniformSizing;
   }

   /**
    * Sets the uniformSizing option.
    * <p>
    * @param uniformSizing the uniformSizing option
    */
   public void setUniformSizing (int uniformSizing) {
      if (uniformSizing == NEITHER || uniformSizing == WIDTH ||
          uniformSizing == HEIGHT  || uniformSizing == BOTH)
         this.uniformSizing = uniformSizing;
   }

   /**
    * Indicates whether the skipInvisible property is set.
    * <p>
    * @return true if invisible components are to be skipped
    */
   public boolean isSkipInvisible() {
      return skipInvisible;
   }

   /**
    * Sets the skipInvisible property.
    * <p>
    * @param skipInvisible true if invisible components are to be skipped
    */
   public void setSkipInvisible (boolean skipInvisible) {
      this.skipInvisible = skipInvisible;
   }

   /**
    * Returns a String representation of this ColumnLayout.
    * <p>
    * @return a String representation of this ColumnLayout
    */
   public String toString() {
      String uniformSizingString = null;
      switch (uniformSizing) {
      case NEITHER:
         uniformSizingString = "NEITHER";
         break;
      case WIDTH:
         uniformSizingString = "WIDTH";
         break;
      case HEIGHT:
         uniformSizingString = "HEIGHT";
         break;
      case BOTH:
         uniformSizingString = "BOTH";
         break;
      }
      return getClass().getName() + "[" + 
                                    "cols=" + cols + 
                                    ",hGap=" + hGap +
                                    ",vGap=" + vGap + 
                                    ",uniformSizing=" + uniformSizingString + 
                                    ",skipInvisible=" + skipInvisible + 
                                    "]";
   }

   /**
    * This method is required by the LayoutManager interface, but is not 
    * used by the ColumnLayout.
    */
   public void addLayoutComponent(String name, Component comp) {
   }

   /**
    * This method is required by the LayoutManager interface, but is not 
    * used by the ColumnLayout.
    */
   public void removeLayoutComponent(Component comp) {
   }

}
