package cw.customermanagementmodul.customer.gui.model;

import java.util.ArrayList;
import java.util.List;

import cw.boardingschoolmanagement.extention.point.CWIDataModelExtentionPoint;
import cw.boardingschoolmanagement.gui.model.CWDataField;
import cw.boardingschoolmanagement.gui.model.CWDataFieldRenderer;
import cw.boardingschoolmanagement.gui.model.CWDataRow;
import cw.customermanagementmodul.customer.gui.renderer.ActiveDataFieldRenderer;
import cw.customermanagementmodul.customer.gui.renderer.GenderDataFieldRenderer;
import cw.customermanagementmodul.customer.persistence.Customer;

/**
 * customer data model 
 * 
 * @author Manuel Geier
 *
 */
public class CustomerDataModel
	implements CWIDataModelExtentionPoint
{

	public Class<?> getBaseClass() {
		return Customer.class;
	}

	public List<CWDataField> getFieldList() {
		List<CWDataField> fields = new ArrayList<CWDataField>();
		
		fields.add(new CWDataField("Anrede") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getGender();
			}
			public CWDataFieldRenderer getFieldRenderer() {
				return new GenderDataFieldRenderer();
			}
		});
		
		fields.add(new CWDataField("Titel") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getTitle();
			}
		});
		
		fields.add(new CWDataField("Vorname") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getForename();
			}
		});
		
		fields.add(new CWDataField("Nachname") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getSurname();
			}
		});
		
		fields.add(new CWDataField("Geburtstag") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getBirthday();
			}
		});
		
		fields.add(new CWDataField("Adresse") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getStreet();
			}
		});
		
		fields.add(new CWDataField("PLZ") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getPostOfficeNumber();
			}
		});
		
		fields.add(new CWDataField("Ort") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getCity();
			}
		});
		
		fields.add(new CWDataField("Bundesland") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getProvince();
			}
		});
		
		fields.add(new CWDataField("Land") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getCountry();
			}
		});
		
		fields.add(new CWDataField("Mobiltelefon") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getMobilephone();
			}
		});
		
		fields.add(new CWDataField("Festnetztelefon") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getLandlinephone();
			}
		});
		
		fields.add(new CWDataField("Fax") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getFax();
			}
		});
		
		fields.add(new CWDataField("Email") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getEmail();
			}
		});
		
		fields.add(new CWDataField("Bemerkung") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getComment();
			}
		});
		
		fields.add(new CWDataField("Aktiv") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).isActive();
			}

			public CWDataFieldRenderer getFieldRenderer() {
				return new ActiveDataFieldRenderer();
			}
		});
		
		fields.add(new CWDataField("Inaktiv seit") {
			public Object getValue(CWDataRow<?> row) {
				return ((Customer)row.getElement()).getInactiveDate();
			}
		});
		
		return fields;
	}
	
	
//
//    private ListModel listModel;
//
//    public CustomerDataModel(List<Customer> customerList) {
//    	
//    }
//    
//    public CustomerDataModel(ListModel listModel) {
//        this.listModel = listModel;
//    }
//
//    public int getRowCount() {
//        return listModel.getSize();
//    }
//
//    @Override
//    public int getColumnCount() {
//        return 18;
//    }
//
//    @Override
//    public String getColumnName(int column) {
//        switch (column) {
//            case 0:
//                return "Anrede";
//            case 1:
//                return "Titel";
//            case 2:
//                return "Vorname";
//            case 3:
//                return "Nachname";
//            case 4:
//                return "Geburtsdatum";
//            case 5:
//                return "Adresse";
//            case 6:
//                return "PLZ";
//            case 7:
//                return "Ort";
//            case 8:
//                return "Bundesland";
//            case 9:
//                return "Staat";
//            case 10:
//                return "Mobiltelefon";
//            case 11:
//                return "Festnetztelefon";
//            case 12:
//                return "Fax";
//            case 13:
//                return "eMail";
//            case 14:
//                return "Bemerkung";
//            case 15:
//                return "Status";
//            case 16:
//                return "Inaktiv seit";
//            default:
//                return "";
//        }
//    }
//
//    @Override
//    public Class<?> getColumnClass(int columnIndex) {
//        switch (columnIndex) {
//            case 0:
//                return Boolean.class;
//            case 5:
//                return Date.class;
//            case 15:
//                return Boolean.class;
//            case 16:
//                return Date.class;
//            default:
//                return String.class;
//        }
//    }
//
//    @Override
//    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        return false;
//    }
//
//    public Object getValueAt(int rowIndex, int columnIndex) {
//        Customer c = (Customer) listModel.getElementAt(rowIndex);
//        switch (columnIndex) {
//            case 0:
//                return c.getGender();
//            case 1:
//                return c.getTitle();
//            case 2:
//                return c.getForename();
//            case 3:
//                return c.getSurname();
//            case 4:
//                return c.getBirthday();
//            case 5:
//                return c.getStreet();
//            case 6:
//                return c.getPostOfficeNumber();
//            case 7:
//                return c.getCity();
//            case 8:
//                return c.getProvince();
//            case 9:
//                return c.getCountry();
//            case 10:
//                return c.getMobilephone();
//            case 11:
//                return c.getLandlinephone();
//            case 12:
//                return c.getFax();
//            case 13:
//                return c.getEmail();
//            case 14:
//                return c.getComment();
//            case 15:
//                return c.isActive();
//            case 16:
//                return c.getInactiveDate();
//            default:
//                return "";
//        }
//    }

}