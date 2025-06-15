package lv.klix.oas.integration;

public interface ApplicationResponse<T> {

    String getId();

    boolean isReady();

    T getOffer();

}
