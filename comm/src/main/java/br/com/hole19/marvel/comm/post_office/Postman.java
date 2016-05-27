package br.com.hole19.marvel.comm.post_office;

import android.content.Context;

import com.android.volley.Request;

import javax.inject.Inject;

import br.com.hole19.marvel.comm.R;
import br.com.hole19.marvel.comm.infrastructure.DaggerWrapper;
import br.com.hole19.marvel.comm.model.EventCatalog;
import br.com.hole19.marvel.comm.util.RequestUtil;
import br.com.hole19.marvel.comm.util.RestTemplate;


/**
 * Created by edgar on 16/02/2016.
 */
public class Postman {

    public static final String TAG = "Postman";

    @Inject
    RestTemplate mRequestService;
    @Inject
    RequestUtil mRequestUtil;

    public Postman() {
        DaggerWrapper.getComponent().inject(this);
    }

    public void listCharacters(Context context, String query, int limit, int offset) {
        this.mRequestService.setUrl(mRequestUtil.buildBasicUrl(context, context.getString(R.string.MARVEL_SERVICE_LIST_CHARACTERS), query, limit, offset));
        this.mRequestService.setBody(EventCatalog.e0000, Request.Method.GET);
        this.mRequestService.execute(context);
    }

    public void listComicDetails(String resourceURI, Context context) {
        if (!"".equals(resourceURI) && context != null) {
            this.mRequestService.setUrl(this.mRequestUtil.parseExistentRequest(resourceURI, context));
            this.mRequestService.setBody(EventCatalog.e0001, Request.Method.GET);
            this.mRequestService.execute(context);
        }
    }

    public void cancelRequests() {
        mRequestService.cancelRequests();
    }
}
