package cw.boardingschoolmanagement.update.pojo;

import java.net.URL;
import java.util.Date;

public class UpdateInformation {
	
	private String name;
    private String version;
    private String systemVersion;
    private Date releaseDate;
    private Date buildDate;
    private URL downloadURL;

    public UpdateInformation(String name, String version, String systemVersion, Date releaseDate, Date buildDate, URL downloadURL) {
        this.name = name;
        this.version = version;
        this.systemVersion = systemVersion;
        this.releaseDate = releaseDate;
        this.buildDate = buildDate;
        this.downloadURL = downloadURL;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public URL getDownloadURL() {
        return downloadURL;
    }

    public String getName() {
        return name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getVersion() {
        return version;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("URL: ");
        str.append(downloadURL);
        return str.toString();
    }
}
