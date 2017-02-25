package aitorpagan.starmoviesimdb.Controller.Delegate;

import android.os.Handler;


import com.koushikdutta.ion.Ion;

import aitorpagan.starmoviesimdb.Controller.MainViewController;
import aitorpagan.starmoviesimdb.Interface.JSONResponse;
import aitorpagan.starmoviesimdb.Interface.NetworkOperationDelegate;
import aitorpagan.starmoviesimdb.Model.FilmResponse;
import aitorpagan.starmoviesimdb.Network.NetworkConstants;
import aitorpagan.starmoviesimdb.Network.NetworkRequestImpl;
import aitorpagan.starmoviesimdb.R;

/**
 * Created by aitorpagan on 23/2/17.
 */

public class MainDelegate implements NetworkOperationDelegate {

    MainViewController activity;
    private int page;
    private int searchPage;
    Handler handler;
    Boolean isPreviousRunning;
    NetworkRequestImpl httpReq;

    public MainViewController getActivity() {
        return activity;
    }

    public void setMainViewController(MainViewController activity) {
        this.activity = activity;
    }

    public MainDelegate(){
        this.page = 1;
        this.handler = new Handler();
        this.isPreviousRunning = false;
    }

    public void updateFilms(int operation, String query){
        if(operation == NetworkConstants.DISCOVER_OP) {
            if(null != httpReq) httpReq.cancelPendingTransactions();
            String url = getActivity().getResources().getString(R.string.discover_url);
            String apikey = getActivity().getResources().getString(R.string.api_key);
            url = String.format(url, apikey);
            url = url + page;
            httpReq = new NetworkRequestImpl(this, new FilmResponse(), activity.getApplicationContext(), url);
            httpReq.setOperation(operation);
            handler.post(httpReq);
        }else if(operation == NetworkConstants.NEWSEARCH_OP){
            if(isPreviousRunning){
                httpReq.cancelPendingTransactions();
            }
            page = 1;
            String url = getActivity().getResources().getString(R.string.search_url);
            String apikey = getActivity().getResources().getString(R.string.api_key);
            url = String.format(url, apikey, query, "1");
            httpReq = new NetworkRequestImpl(this, new FilmResponse(), activity.getApplicationContext(), url);
            httpReq.setOperation(operation);
            handler.post(httpReq);
            isPreviousRunning = true;
        }else if(operation == NetworkConstants.SEARCH_OP){
            if(isPreviousRunning){
                httpReq.cancelPendingTransactions();
            }
            String url = getActivity().getResources().getString(R.string.search_url);
            String apikey = getActivity().getResources().getString(R.string.api_key);
            url = String.format(url, apikey, query, String.valueOf(searchPage));
            httpReq = new NetworkRequestImpl(this, new FilmResponse(), activity.getApplicationContext(), url);
            httpReq.setOperation(operation);
            handler.post(httpReq);
            isPreviousRunning = true;
        }

    }

    @Override
    public void preExecuteNeworkRequest(int operation) {
        if(page > 1 || operation == NetworkConstants.SEARCH_OP){
            activity.container.startLoading();
        }
        if(operation == NetworkConstants.NEWSEARCH_OP){
            activity.searchBegan();
            searchPage = 1;
        }
    }

    @Override
    public void processNetworkResponse(int operation, JSONResponse response) {
        isPreviousRunning = false;
        if(operation == NetworkConstants.DISCOVER_OP){
            activity.container.stopLoading();
            activity.container.addFilms(((FilmResponse)response).getFilms());
            page++;
        }else if(operation == NetworkConstants.NEWSEARCH_OP){
            activity.container.stopLoading();
            activity.container.addFilms(((FilmResponse)response).getFilms());
            searchPage++;
        }else if(operation == NetworkConstants.SEARCH_OP){
            activity.container.stopLoading();
            activity.container.addFilms(((FilmResponse)response).getFilms());
            searchPage++;
        }

    }
}
