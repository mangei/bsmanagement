package cw.tweetmodule.extention;

import java.awt.BorderLayout;
import java.awt.Font;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.extention.point.CWIViewExtentionPoint;
import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.boardingschoolmanagement.gui.HomeView;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.images.CWImageDefinition;
import cw.boardingschoolmanagement.manager.PropertiesManager;
import cw.tweetmodule.images.ImageDefinitionTweet;
import cw.tweetmodule.logic.BoTweet;

/**
 *
 * @author ManuelG
 */
public class TweetHomeExtention
    implements CWIViewExtentionPoint<HomeView, HomePresentationModel>
{

    private CWView view;
	private HomeView baseView;
	private CWPanel contentPanel;

    public void initPresentationModel(HomePresentationModel baseModel) {
    }

    public CWView<?> getView() {
        return view;
    }

	@Override
	public Class<?> getExtentionClass() {
		return null;
	}

	@Override
	public List<Class<?>> getExtentionClassList() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(HomeView.class);
		list.add(HomePresentationModel.class);
		return list;
	}

	@Override
	public void initView(HomeView baseView) {
		
		if(BoTweet.isActive()) {
			
			view = new CWView(null);
	        view.setHeaderInfo(new CWView.CWHeaderInfo(
	                "Neuerste Tweets",
	                "",
	                CWUtils.loadIcon(ImageDefinitionTweet.TWITTER),
	                CWUtils.loadIcon(ImageDefinitionTweet.TWITTER)
	        ));
	        contentPanel = CWComponentFactory.createPanel();
	        view.addToContentPanel(contentPanel);
	        contentPanel.add(new JLabel("wird geladen...", CWUtils.loadIcon(CWImageDefinition.LOADING), SwingConstants.LEFT));
	
	        baseView.addToContentPanel(view);
	        
	        new Thread(new Runnable() {
	
	            public void run() {
	
	                try 
	                {
	
	                    String username = PropertiesManager.getProperty("tweetmodule.username");
	                    String password = PropertiesManager.getProperty("tweetmodule.password");
	
	                    if(username == null || username.isEmpty() || password == null || password.isEmpty()) {
	                    	contentPanel.removeAll();
	                    	contentPanel.add(new JLabel("Verbindungsdaten sind nicht vollstaendig. Aendern Sie diese in den Einstellungen"), BorderLayout.CENTER);
	                    	contentPanel.revalidate();
	                        return;
	                    }
	
	                    Twitter t = new Twitter(username, password);
	                    List<Status> userTimeline = t.getUserTimeline(username);
	
	                    view.getHeaderInfo().setHeaderText(view.getHeaderInfo().getHeaderText() + " von " + t.getScreenName());
	                    ImageIcon profileImage = null;
	                    try {
	                        profileImage = new ImageIcon(t.getUser(username).getProfileImageUrl().toURL());
	                    } catch (MalformedURLException ex) {
	                        ex.printStackTrace();
	                    }
	                    view.getHeaderInfo().setIcons(profileImage, profileImage);
	
	                    CWPanel p = new CWPanel(new FormLayout(
	                            "pref, 10dlu, pref",
	                            "pref, 2dlu, pref, 2dlu, pref"));
	                    CellConstraints cc = new CellConstraints();
	
	                    for (int i = 0, l = userTimeline.size(); i < 3 && i < l; i++) {
	                        Status s = userTimeline.get(i);
	
	                        Date d = s.getCreatedAt();
	                        Calendar c = new GregorianCalendar();
	                        c.setTime(d);
	                        String dtext = c.get(Calendar.DAY_OF_MONTH) + ". " + (c.get(Calendar.MONTH) + 1) + ". " + c.get(Calendar.YEAR) + ":";
	
	                        JLabel lDate = new JLabel(dtext);
	                        lDate.setFont(lDate.getFont().deriveFont(Font.ITALIC));
	                        p.add(lDate, cc.xy(1, i * 2 + 1));
	                        p.add(new JLabel(s.getText()), cc.xy(3, i * 2 + 1));
	                    }
	
	                    contentPanel.removeAll();
	                    contentPanel.add(p, BorderLayout.CENTER);
	                    contentPanel.revalidate();
	
	                } catch (TwitterException twitterException) {
	                    // If there is an error
	                    
	                    twitterException.printStackTrace();
	                    
	                    contentPanel.removeAll();
	                    contentPanel.add(new JLabel("<html><body>Tweets konnten nicht geladen werden...</body></html>"), BorderLayout.CENTER);
	                    contentPanel.revalidate();
	                }
	
	            }
	        }).start();
		}
	}

}
