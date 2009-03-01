package cw.boardingschoolmanagement.gui;

import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditPropertyView extends javax.swing.JDialog {

    private EditPropertyPresenter model;
    
    public JLabel lName;
    public JLabel lValue;
    public JButton bCancel;
    public JButton bOk;
    public JTextField tfName;
    public JTextField tfValue;
    
//    /**
//     * Elemente werden erzeugt ohne sie mit Daten zu füllen. Daten müssen nachträglich
//     * mit der Methode setModel(...) gefüllt werden.
//     */
//    public EditPropertyView(Window parent) {
//        this(parent,null);
//    }

    public static int OK_OPTION = 1;
    public static int CANCEL_OPTION = 2;
    
    private static int option;
    public static int showEditPropertyView(Window parent, EditPropertyPresenter model, String title) {
        option = CANCEL_OPTION;
        
        final EditPropertyView view = new EditPropertyView(parent, model,title);
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                option = CANCEL_OPTION;
                view.setVisible(false);
                view.dispose();
            }
        });
        view.bCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option = CANCEL_OPTION;
                view.setVisible(false);
                view.dispose();
            }
        });
        view.bOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                option = OK_OPTION;
                view.setVisible(false);
                view.dispose();
            }
        });
        view.setVisible(true);
        
        return option;
    }
    
    public EditPropertyView(Window parent, EditPropertyPresenter model, String title) {
        super(parent, title, ModalityType.APPLICATION_MODAL);
        this.model = model;
        
        // Dem Model sagen, wer die View ist
//        if(model != null)
            model.setView(this);
        
        initComponents();
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        CWUtils.centerWindow(parent, this);
        
        setResizable(false);
//        setMinimumSize(getPreferredSize());
//        setVisible(true);
    }
  
    private void initComponents() {

        lName = new JLabel();
        tfName = new JTextField();
        lValue = new JLabel();
        tfValue = new JTextField();
        bOk = new JButton();
        bCancel = new JButton();

//        if(model!=null)
            initModels();

        CellConstraints ccMain = new CellConstraints();
        getContentPane().setLayout(new FormLayout(
                "pref:grow",
                "pref, 1dlu, pref"
        ));
        
        
        /**
         * Boxes
         */
        FormLayout layout = new FormLayout(
                "right:max(pref;30dlu), 4dlu, max(pref;100dlu):grow",
                "pref, 4dlu, pref"
        );
        
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();
        
        CellConstraints cc = new CellConstraints();
        builder.add(lName,              cc.xy(1, 1));
        builder.add(tfName,             cc.xy(3, 1));
        builder.add(lValue,             cc.xy(1, 3));
        builder.add(tfValue,            cc.xy(3, 3));
        
        getContentPane().add(builder.getPanel(), ccMain.xy(1, 1));
        
        /**
         * Buttons
         */
        layout = new FormLayout(
                "right:pref:grow, 4dlu, pref",
                "pref"
        );
        
        builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();
        
        cc = new CellConstraints();
        builder.add(bOk,        cc.xy(1, 1));
        builder.add(bCancel,    cc.xy(3, 1));
        
        getContentPane().add(builder.getPanel(), ccMain.xy(1, 3));

        
        pack();
    }
    
    private void initModels() {
        lName.setText(model.getNameLabelModel().toString());
        lValue.setText(model.getValueLabelModel().toString());
        
        tfName.setDocument(model.getNameDocumentModel());
        tfValue.setDocument(model.getValueDocumentModel());
        
        bOk.setModel(model.getOKButtonModel());
        bOk.setAction(model.getOKButtonAction());

        bCancel.setModel(model.getCancelButtonModel());
        bCancel.setAction(model.getCancelButtonAction());
    }

    public EditPropertyPresenter getModel() {
        return model;
    }

    public void setModel(EditPropertyPresenter model) {
        this.model = model;
        model.setView(this);
        initModels();
        validate();
    }
    
}
