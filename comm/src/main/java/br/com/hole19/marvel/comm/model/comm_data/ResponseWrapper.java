package br.com.hole19.marvel.comm.model.comm_data;

import java.io.Serializable;

/**
 * Created by edgar on 17/02/2016.
 */
public class ResponseWrapper implements Serializable {

    private ResponseType type;
    private Object payload;

    public ResponseWrapper() {
    }

    public ResponseWrapper(Object payload, ResponseType type) {
        this.payload = payload;
        this.type = type;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }
}
