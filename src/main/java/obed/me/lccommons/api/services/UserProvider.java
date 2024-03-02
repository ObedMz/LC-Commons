package obed.me.lccommons.api.services;

import lombok.Getter;
import obed.me.lccommons.api.entities.User;

import java.util.concurrent.ConcurrentHashMap;

@Getter
public class UserProvider {
    private static volatile UserProvider instance;
    private final WebClient apiClient = WebClient.getInstance();
    private ConcurrentHashMap<String, User> usersCache = new ConcurrentHashMap<>();
    private final String ENDPOINT = EndPointType.USER.getEndPoint();
    private UserProvider() {
    }

    public static UserProvider getInstance(){
        if(instance == null){
            synchronized (UserProvider.class){
                if(instance == null)
                    instance = new UserProvider();
            }
        }
        return instance;
    }

    public User createUser(User user) {
        return apiClient.create(ENDPOINT, user, User.class);
    }

    public User getUserByName(String name) {
        return apiClient.get(ENDPOINT.concat(name), User.class);
    }
    public void deleteUser(String name){
        apiClient.delete(ENDPOINT.concat(name));
    }
}
