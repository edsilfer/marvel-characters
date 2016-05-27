package br.com.hole19.marvel.comm.model.marvel;

import java.io.Serializable;

/**
 * Created by edgar on 29-Apr-16.
 */
public class Thumbnail implements Serializable {

    private String path;
    private String extension;
    private String url;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getUrl() {
        return this.path.concat(".").concat(this.extension);
    }
}
