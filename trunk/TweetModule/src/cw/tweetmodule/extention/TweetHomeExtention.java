package cw.tweetmodule.extention;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.extention.point.HomeExtentionPoint;
import cw.boardingschoolmanagement.gui.HomePresentationModel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.manager.PropertiesManager;
import java.awt.BorderLayout;
import java.awt.Font;
import java.net.MalformedURLException;
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

/**
 *
 * @author ManuelG
 */
public class TweetHomeExtention
    implements HomeExtentionPoint
{

    public static void main(String[] args) {
        new TweetHomeExtention();
    }

    public TweetHomeExtention() {

//        // Make a Twitter object
//	Twitter t = new Twitter("mangei","czzA7pZdIVkJY8reM6kA");
//	// Print Daniel Winterstein's status
//	System.out.println(t.getStatus("mangei"));
//        System.out.println(t.getFollowers().size());
////        printMessages(t.getDirectMessagesSent());
////        printMessages(t.getFavorites("mangei"));
//        printMessages(t.getUserTimeline("mangei"));
////        printMessages(t.getFriendsTimeline());
////        printMessages(t.getHomeTimeline());
//	// Set my status
////	twitter.setStatus("Messing about in Java");
//

    }

    public void printMessages(List l) {
        for (int i = 0; i < l.size(); i++) {
            System.out.println("" + (i+1) + ": " + l.get(i));
        }
    }

    private CWView view;

    public void initPresentationModel(HomePresentationModel homePresentationModel) {
        view = new CWView(new CWView.CWHeaderInfo(
                "Neuerste Tweets",
                "",
                CWUtils.loadIcon("cw/tweetmodule/images/twitter11.png"),
                CWUtils.loadIcon("cw/tweetmodule/images/twitter11.png")
        ));
        view.getContentPanel().add(new JLabel("wird geladen...", CWUtils.loadIcon("cw/boardingschoolmanagement/images/loadingicon.gif"), SwingConstants.LEFT));

        new Thread(new Runnable() {

            public void run() {

                try 
                {

                    String username = PropertiesManager.getProperty("tweetmodule.username");
                    String password = PropertiesManager.getProperty("tweetmodule.password");

                    if(username == null || username.isEmpty() || password == null || password.isEmpty()) {
                        view.getContentPanel().removeAll();
                        view.getContentPanel().add(new JLabel("Verbindungsdaten sind nicht vollständig. Ändern Sie diese in den Einstellungen"), BorderLayout.CENTER);
                        view.getContentPanel().revalidate();
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

                    view.getContentPanel().removeAll();
                    view.getContentPanel().add(p, BorderLayout.CENTER);
                    view.getContentPanel().revalidate();

                } catch (TwitterException twitterException) {
                    // If there is an error
                    
                    twitterException.printStackTrace();

                    view.getContentPanel().removeAll();
                    view.getContentPanel().add(new JLabel("Tweets konnten nicht geladen werden..."), BorderLayout.CENTER);
                    view.getContentPanel().revalidate();
                }

            }
        }).start();
        
    }

    public Object getModel() {
        return null;
    }

    public CWPanel getView() {
        return view;
    }

    public void dispose() {
        view.dispose();
    }

}
