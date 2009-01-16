package cw.boardingschoolmanagement.gui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Manuel Geier
 */
public class JIntegerTextField
extends JTextField
{

    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;
    
    public JIntegerTextField() {
        this("");
    }
    
    public JIntegerTextField(String text) {
        super(text);
        
        getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                update(e);
            }

            public void removeUpdate(DocumentEvent e) {
                update(e);
            }

            public void changedUpdate(DocumentEvent e) {
                update(e);
            }
            
            
            
            private void update(DocumentEvent e) {
                JTextField tf = ((JTextField)JIntegerTextField.this);

            }
            
        });
        
        setInputVerifier(new InputVerifier() {
            
            private BufferedImage errorImg;
            private JPanel panel;
            private Color normalColor;
            
            @Override
            public boolean verify(JComponent input) {
                JTextField tf = ((JTextField)input);
                
                // Beim ersten Mal die Standardhintergrundfarbe erfragen
                if(normalColor==null) {
                    normalColor = tf.getBackground();
                }
                
                if(!check(tf.getText())) {
                    
                    // Wenn der Inhalt NICHT OK ist
                    
                    System.out.println("Panelxxx: " + panel);
                    // Wenn das Panel nicht existiert, soll es erstellt werden
                    if(panel==null) {
                        
                        // Neues Panel für das Icon
                        tf.add(panel = new JPanel() {

                            @Override
                            public void paint(Graphics g) {
                                
                                // Bild laden, wenn es noch nicht geladen wurde
                                if(errorImg == null) {
//                                    errorImg = CWUtils.loadImage("images/exclamation.png");
                                    try {
                                        errorImg = ImageIO.read(new File("images/icons/exclamation.png"));
                                    } catch (IOException ex) {
                                        Logger.getLogger(JIntegerTextField.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                g.drawImage(errorImg, 0, 0, null);
                            }

                        });
                        
                        // Die Position des Icons setzen
                        int width = tf.getBounds().width - 16 - 2;
                        panel.setBounds(width, 2, 16, 16);
                        
                        // Neu zeichnen
                        tf.validate();
                    }
                    
                    // Hintergrund setzten
//                    tf.setBackground(Color.red.brighter().brighter().brighter());
                    
                    tf.setMargin(new Insets(0, 0, 0, 17));
                    
                    return false;
                }
                
                // Wenn alles OK ist
                
                // Panel entfernen
                System.out.println("panel: " + panel);
                if(panel!=null) {
                    tf.remove(panel);
                    panel = null;
                    tf.repaint();
                }
                
                // Normale Hintergrundfarbe wieder setzen
                tf.setBackground(normalColor);
                
                tf.setMargin(new Insets(0, 0, 0, 0));
                
                return true;
            }
        });
    }
    
    private boolean check(String value) {
        int i;

        if(value.length() == 0)
            return true;
        
        // Auf Integer prüfen
        try {
            i = Integer.parseInt(value);
        } catch (NumberFormatException numberFormatException) {
            return false;
        }

        // Größe prüfen
        if(i < min || i > max) {
            return false;
        }

        return true;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

}
