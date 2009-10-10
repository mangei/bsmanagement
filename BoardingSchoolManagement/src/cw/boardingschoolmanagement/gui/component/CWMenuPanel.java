package cw.boardingschoolmanagement.gui.component;

import cw.boardingschoolmanagement.gui.ui.ActiveRoundMenuButtonUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jvnet.lafwidget.animation.FadeConfigurationManager;
import org.jvnet.lafwidget.animation.FadeKind;

/**
 *
 * @author Manuel Geier
 */
public class CWMenuPanel
        extends JList {

    private List<Category> categories;
    private List jListList;
    private Item currentItem = new Item(new AbstractButton() {}, 0);
    private Item startItem;

    public CWMenuPanel() {
        categories = new ArrayList<Category>();
        jListList = new ArrayList();

        setOpaque(false);
        setFocusable(false);

        FadeConfigurationManager.getInstance().disallowFades(FadeKind.ARM, this);
        FadeConfigurationManager.getInstance().disallowFades(FadeKind.ENABLE, this);
        FadeConfigurationManager.getInstance().disallowFades(FadeKind.FOCUS, this);
        FadeConfigurationManager.getInstance().disallowFades(FadeKind.FOCUS_LOOP_ANIMATION, this);
        FadeConfigurationManager.getInstance().disallowFades(FadeKind.GHOSTING_BUTTON_PRESS, this);
        FadeConfigurationManager.getInstance().disallowFades(FadeKind.GHOSTING_ICON_ROLLOVER, this);
        FadeConfigurationManager.getInstance().disallowFades(FadeKind.ICON_GLOW, this);
        FadeConfigurationManager.getInstance().disallowFades(FadeKind.PRESS, this);
        FadeConfigurationManager.getInstance().disallowFades(FadeKind.ROLLOVER, this);
        FadeConfigurationManager.getInstance().disallowFades(FadeKind.SELECTION, this);

//        putClientProperty(LafWidget.ANIMATION_KIND, AnimationKind.NONE);

//        setUI(new BasicListUI() {
//            protected void installListeners()
//            {
//                super.installListeners();
//                list.removeFocusListener(focusListener);
//            }
//        });
        setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, new Color(178, 187, 200)));

        setSelectionModel(new DefaultListSelectionModel() {

            @Override
            public void setSelectionInterval(int index0, int index1) {

                Object o = jListList.get(index0);
                if (o instanceof Category) {
                } else {
                    super.setSelectionInterval(index0, index1);
                }
            }

        });
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                // If the selection is empty, set to the last item
                if (getSelectionModel().isSelectionEmpty()) {
                    getSelectionModel().setSelectionInterval(e.getLastIndex(), e.getLastIndex());
                } else {
                    int index = getSelectedIndex();
                    if (index != -1) {
                        Item item = (Item) jListList.get(index);
                        if (!item.equals(currentItem)) {
                            currentItem = item;
                            item.getItem().doClick();
                        }
                    }
                }
            }
        });
        setDragEnabled(false);
        setCellRenderer(new Renderer());
    }

    /**
     * Add an new Item
     * @param item AbstractButton of the item
     * @param category_key Key of the Category of the item
     * @return this JMenuPanel
     */
    public CWMenuPanel addItem(AbstractButton item, String category_key) {
        addItem(item, category_key, 0, false);
        return this;
    }

    /**
     * Add an new Item
     * @param item AbstractButton of the item
     * @param category_key Key of the Category of the item
     * @param startpage if the item is the startpage
     * @return this JMenuPanel
     */
    public CWMenuPanel addItem(AbstractButton item, String category_key, boolean startpage) {
        addItem(item, category_key, 0, startpage);
        return this;
    }

    /**
     * Add an new Item
     * @param item AbstractButton of the item
     * @param category_key Key of the Category of the item
     * @param priority Orderpriority of the item in the category
     * @return this JMenuPanel
     */
    public CWMenuPanel addItem(AbstractButton item, String category_key, int priority) {
        addItem(item, category_key, priority, false);
        return this;
    }

    /**
     * Add an new Item
     * @param item AbstractButton of the item
     * @param category_key Key of the Category of the item
     * @param priority Orderpriority of the item in the category
     * @param startpage if the item is the startpage
     * @return this JMenuPanel
     */
    public CWMenuPanel addItem(AbstractButton item, String category_key, int priority, boolean startpage) {
        Category c = getCategoryByKey(category_key);
        if (c != null) {
            Item item2 = new Item(item, priority);

            // If the item wants to be the startpage and there is no startpage than it is the startpage
            if (startpage && startItem == null) {
                startItem = item2;
            }
            c.getItems().add(item2);
            positionElements();
        }
        return this;
    }

    /**
     * Fügt eine Kategorie zur Sidepare hinzu
     * @param name Name of the category
     * @param key Key of the category
     * @return this JMenuPanel
     */
    public CWMenuPanel addCategory(String name, String key) {
        addCategory(name, key, 0);
        return this;
    }

    /**
     * Fügt eine Kategorie zur Sidepare hinzu
     * @param name Name of the category
     * @param key Key of the category
     * @param priority Anordnungsreihenfolge, je höher der Wert dest weiter oben im Baum wird die Kategorie angelegt
     * @return this JMenuPanel
     */
    public CWMenuPanel addCategory(String name, String key, int priority) {
        categories.add(new Category(name, key, priority));
        positionElements();
        return this;
    }

    public void loadStartItem() {
        if (startItem != null) {
            setSelectedIndex(startItem.getIndex());
            startItem.getItem().doClick();
        }
    }

    /**
     * Return the Category by the keyname. If the key doesn't exitsts it will return null.
     * @param key Key
     * @return Category
     */
    private Category getCategoryByKey(String key) {
        Category cat = null;

        for (Category c : categories) {
            if (c.getKey().equals(key)) {
                cat = c;
                break;
            }
        }

        return cat;
    }

    private void positionElements() {
        jListList.clear();

        Category c;
        List<Item> itemlist;
        Item item;
        int index = 0;

        Collections.sort(categories);
        for (int i = 0, l = categories.size(); i < l; i++) {
            c = categories.get(i);
            c.setIndex(index++);

            jListList.add(c);

            Collections.sort(c.getItems());
            itemlist = c.getItems();
            for (int j = 0, k = itemlist.size(); j < k; j++) {
                item = itemlist.get(j);
                item.setIndex(index++);

                jListList.add(item);
            }
        }

        setListData(jListList.toArray());
    }
    private static final Color LIGHT_VIOLET_COLOR = new Color(201, 208, 218);
    private static final Color BLACK_LOW_OPACITY_COLOR = new Color(0, 0, 0, 20);

    @Override
    protected void paintComponent(Graphics g) {
//        Rectangle rect = new Rectangle(0, 0, getWidth(), getHeight());
//        JideSwingUtilities.fillGradient((Graphics2D) g, rect, new Color(201,208,218).getRGB());


        // Eine Farbe
        g.setColor(LIGHT_VIOLET_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Verlauf
//        JideSwingUtilities.fillGradient((Graphics2D) g, rect, new Color(201,208,218), new Color(168, 177, 189), true);


//        // Top Line
//        g.setColor(new Color(178,187,200));
//        g.drawLine(0, 0, getWidth(), 0);
        setOpaque(false);
        super.paintComponent(g);

        if (!isEnabled()) {
            g.setColor(BLACK_LOW_OPACITY_COLOR);
            g.fillRect(0, 0, getWidth(), getHeight());

            int abs = 5;

            for (int i = abs, h = getHeight(); i < h; i++) {
                if (i % abs == 0) {
                    g.drawLine(0, i, h, i);
                }
            }
        }
    }

    private class Renderer
            implements ListCellRenderer {

        private JLabel lCategory;
        private JLabel lSelectedItem;
        private JLabel lNotSelectedItem;

        private final Color COLOR1 = new Color(126,154,192);
        private final Color COLOR2 = new Color(73,106,156);
        private final Color COLOR3 = new Color(69,101,152);
        private final Color COLOR4 = new Color(201,208,218);
        private final Border EMPTYBORDER1 = BorderFactory.createEmptyBorder(2, 25, 2, 5);
        private final Border EMPTYBORDER2 = BorderFactory.createEmptyBorder(5, 10, 1, 5);

        public Renderer() {

//            lSelectedItem = new JLabel() {
//                @Override
//                protected void paintComponent(Graphics g) {
//                    int height = getHeight();
//                    int width = getWidth();
//
//                    // Blue Background Gradient
//                    Rectangle rect = new Rectangle(0, 0, width, height);
//                    JideSwingUtilities.fillGradient((Graphics2D) g, rect, COLOR1, COLOR2, true);
//
//                    // Top Line
//                    g.setColor(COLOR3);
//                    g.drawLine(0, 0, width, 0);
//
//                    // Bottom Line
//                    g.setColor(COLOR3);
//                    g.drawLine(0, height, width, height);
//
//                    super.paintComponent(g);
//                }
//            };
            lSelectedItem = new JLabel();
            lSelectedItem.setUI(new ActiveRoundMenuButtonUI());
            lSelectedItem.setForeground(Color.WHITE);
            lSelectedItem.setFont(getFont().deriveFont(Font.PLAIN));
            lSelectedItem.setBorder(EMPTYBORDER1);
            lSelectedItem.setOpaque(false);

            lNotSelectedItem = new JLabel();
            lNotSelectedItem.setFont(getFont().deriveFont(Font.PLAIN));
            lNotSelectedItem.setBorder(EMPTYBORDER1);
            lNotSelectedItem.setOpaque(false);
            lNotSelectedItem.setBackground(COLOR4);

            lCategory = new JLabel();
            lCategory.setFont(getFont().deriveFont(Font.BOLD));
            lCategory.setOpaque(false);
            lCategory.setBorder(EMPTYBORDER2);
            lCategory.setBackground(COLOR4);
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String str = (value == null) ? "" : value.toString();

            if (value instanceof Category) {
                lCategory.setText(str.toUpperCase());
                return lCategory;
            } else {
                Item item = (Item) value;

                if (isSelected) {
                    lSelectedItem.setText(str);
                    lSelectedItem.setIcon(item.getItem().getIcon());
                    return lSelectedItem;
                } else {
                    lNotSelectedItem.setText(str);
                    lNotSelectedItem.setIcon(item.getItem().getIcon());
                    return lNotSelectedItem;
                }
            }
        }
    }

    private class Category
            implements Comparable {

        private String name;
        private String key;
        private int priority;
        private JLabel label;
        private List<Item> items;
        private int index = -1;

        public Category(String name, String key, int priority) {
            this.name = name;
            this.key = key;
            this.priority = priority;
            label = new JLabel(name);
            items = new ArrayList<Item>();
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public int getPriority() {
            return priority;
        }

        public JLabel getLabel() {
            return label;
        }

        public List<Item> getItems() {
            return items;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int compareTo(Object o) {
            Category c2 = (Category) o;
            if (this.priority < c2.priority) {
                return 1;
            } else if (this.priority > c2.priority) {
                return -1;
            } else {
                return 0;
            }
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private class Item
            implements Comparable {

        private AbstractButton item;
        private int priority;
        private int index = -1;

        public Item(AbstractButton item, int priority) {
            this.item = item;
            this.priority = priority;
        }

        public AbstractButton getItem() {
            return item;
        }

        public void setItem(JMenuItem item) {
            this.item = item;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int compareTo(Object o) {
            Item i2 = (Item) o;
            if (this.priority < i2.priority) {
                return 1;
            } else if (this.priority > i2.priority) {
                return -1;
            } else {
                return 0;
            }
        }

        @Override
        public String toString() {
            return item.getText();
        }
    }

    public void lock() {
        if (isEnabled()) {
            this.setEnabled(false);
            this.repaint();
        }
    }

    public void unlock() {
        if (!isEnabled()) {
            this.setEnabled(true);
            this.repaint();
        }
    }
}
