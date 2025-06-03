package Service;

import Repository.MyRepository;

public class MyService {
	private final MyRepository myRepository;
    // Constructor with dependency injection
    public MyService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }
    public String getData() {
        return myRepository.getData(); // Delegates to the repository
    }
    public void saveData(String data) {
        // Only save data if it's not empty
        if (data != null && !data.isEmpty()) {
            myRepository.save(data);
        }
    }
}
