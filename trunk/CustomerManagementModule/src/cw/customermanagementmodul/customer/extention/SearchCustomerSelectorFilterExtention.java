package cw.customermanagementmodul.customer.extention;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.customer.extention.point.CustomerSelectorFilterExtentionPoint;
import cw.customermanagementmodul.customer.gui.SearchCustomerSelectorFilterExtentionPresentationModel;
import cw.customermanagementmodul.customer.gui.SearchCustomerSelectorFilterExtentionView;
import cw.customermanagementmodul.customer.persistence.Customer;

/**
 *
 * @author ManuelG
 */
public class SearchCustomerSelectorFilterExtention
        implements CustomerSelectorFilterExtentionPoint {

    private SearchCustomerSelectorFilterExtentionPresentationModel model;
    private SearchCustomerSelectorFilterExtentionView view;
    private ValueModel change;

    private PropertyChangeListener changeListener;

    public void init(final ValueModel change, EntityManager entityManager) {
        this.change = change;

        model = new SearchCustomerSelectorFilterExtentionPresentationModel(entityManager);
        view = new SearchCustomerSelectorFilterExtentionView(model);
    }

    public void initEventHandling() {
        model.getSearchModel().addValueChangeListener(changeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                change.setValue(true);
            }
        });
    }

    public List<Customer> filter(List<Customer> costumers) {

        // Get the input
        String searchString = ((String) model.getSearchModel().getValue()).toLowerCase();

        // If there is no input, don't filter
        if(searchString.isEmpty()) {
            return costumers;
        }

        List<Customer> newCostumers = new ArrayList<Customer>();

        // Split the searchString
        String[] searchStrings = searchString.split(" ");

        for(int i=0; i< searchStrings.length; i++) {
            System.out.println(" - " + i + " '" + searchStrings[i] + "'");
        }

        Iterator<Customer> it = costumers.iterator();
        Customer costumer;
        StringBuilder costumerStringBuilder;
        String costumerString;
        while(it.hasNext()) {

            costumer = it.next();

            costumerStringBuilder = new StringBuilder();

            costumerStringBuilder.append(costumer.getId().toString());
            costumerStringBuilder.append(';');
            costumerStringBuilder.append(costumer.getTitle());
            costumerStringBuilder.append(';');
            costumerStringBuilder.append(costumer.getForename());
            costumerStringBuilder.append(';');
            costumerStringBuilder.append(costumer.getSurname());
            costumerStringBuilder.append(';');
            costumerStringBuilder.append(costumer.getStreet());
            costumerStringBuilder.append(';');
            costumerStringBuilder.append(costumer.getCity());
            costumerStringBuilder.append(';');
            costumerStringBuilder.append(costumer.getProvince());
            costumerStringBuilder.append(';');
            costumerStringBuilder.append(costumer.getCountry());
            costumerStringBuilder.append(';');
            costumerStringBuilder.append(costumer.getMobilephone());
            costumerStringBuilder.append(';');
            costumerStringBuilder.append(costumer.getLandlinephone());
            costumerStringBuilder.append(';');
            costumerStringBuilder.append(costumer.getEmail());
            costumerStringBuilder.append(';');
            costumerStringBuilder.append(costumer.getFax());
            costumerStringBuilder.append(';');
            costumerStringBuilder.append(costumer.getComment());

            costumerString = costumerStringBuilder.toString().toLowerCase();

            boolean found = true;

            for(int i=0, l=searchStrings.length; i<l; i++) {

//                if(!(      /*costumer.getId().toString().contains(searchStrings[i])
//                        ||*/ costumer.getTitle().toLowerCase().contains(searchStrings[i])
//                        || costumer.getForename().toLowerCase().contains(searchStrings[i])
//                        || costumer.getForename2().toLowerCase().contains(searchStrings[i])
//                        || costumer.getSurname().toLowerCase().contains(searchStrings[i])
//                        || costumer.getStreet().toLowerCase().contains(searchStrings[i])
//                        || costumer.getCity().toLowerCase().contains(searchStrings[i])
//                        || costumer.getProvince().toLowerCase().contains(searchStrings[i])
//                        || costumer.getCountry().toLowerCase().contains(searchStrings[i])
//                        || costumer.getMobilephone().contains(searchStrings[i])
//                        || costumer.getLandlinephone().contains(searchStrings[i])
//                        || costumer.getEmail().toLowerCase().contains(searchStrings[i])
//                        || costumer.getFax().toLowerCase().contains(searchStrings[i])
//                        || costumer.getComment().toLowerCase().contains(searchStrings[i])
//                        || costumer.getBirthday().toString().contains(searchStrings[i])
//                        )) {

                if(!(costumerString.contains(searchStrings[i]))){
                    found = false;
                    break;
                }
            }

            if(found) {
                newCostumers.add(costumer);
            }
            
        }

        return newCostumers;
    }

    public String getPosition() {
        return BorderLayout.NORTH;
    }

    public CWPanel getView() {
        return view;
    }

    public void dispose() {
        view.dispose();
        changeListener = null;
    }

    public String getFilterName() {
        return "Kundendaten";
    }
}
