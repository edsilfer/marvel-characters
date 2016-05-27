package br.com.hole19.marvel.comm.infrastructure;

import br.com.hole19.marvel.comm.util.RequestUtil;
import br.com.hole19.marvel.comm.util.RestTemplate;
import dagger.Module;
import dagger.Provides;

/**
 * Created by edgar on 09-May-16.
 */
@Module
public class ModuleUtil {

    @Provides
    public RestTemplate provideRestTemplate() {
        return new RestTemplate();
    }

    @Provides
    public RequestUtil provideRequestUtil() {
        return new RequestUtil();
    }

}
