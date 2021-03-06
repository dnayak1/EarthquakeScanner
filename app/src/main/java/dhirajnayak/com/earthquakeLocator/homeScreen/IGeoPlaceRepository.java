package dhirajnayak.com.earthquakeLocator.homeScreen;

/**
 * Created by dhirajnayak on 1/27/18.
 * interface for repository
 */

public interface IGeoPlaceRepository {
    public void dataFromNetwork(String starttime, String endtime);
    public void dataFromNetworkWithMag(String starttime, String endtime, String minmagnitude);
    public void dataFromNetworkCustomized(String starttime, String endtime, String latitude,String longitude,String maxradiuskm);
}
