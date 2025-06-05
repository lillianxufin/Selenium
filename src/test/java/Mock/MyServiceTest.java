package Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import Repository.MyRepository;
import Service.MyService;

@ExtendWith(MockitoExtension.class)
public class MyServiceTest {
    @Mock
    private MyRepository myRepository;  // MyRepository is mocked, no need to initiate it
    @InjectMocks
    private MyService myService;  // MyService gets dependencies injected
    @Test
    void testGetData() {
        // Arrange
       when(myRepository.getData()).thenReturn("Mocked Data");
        // Act
        String result = myService.getData();
        // Assert
        assertEquals("Mocked Data", result);
        verify(myRepository).getData();
    }
  @Test
    void testSaveData() {
        // Act: Call the method being tested
        myService.saveData("Some data");
        // Assert: Verify the repository's save method was called with "Some data"
        verify(myRepository).save("Some data");
    }
    @Test
    void testSaveDataWhenEmpty() {
        // Act: Call the method with empty data
        myService.saveData("");
        // Assert: Verify that save() is not called when the data is empty
        verifyNoInteractions(myRepository);
    }
}

