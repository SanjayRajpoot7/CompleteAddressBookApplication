import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';  // Import HttpClientTestingModule for HTTP requests
import { AddressBookService } from './app.service';  // Import the service you're testing

describe('AppService', () => {
  let service: AddressBookService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],  // Import HttpClientTestingModule to mock HTTP calls
      providers: [AddressBookService],  // Provide AppService here
    });
    service = TestBed.inject(AddressBookService);
    httpMock = TestBed.inject(HttpTestingController);  // Inject HttpTestingController to mock HTTP requests
  });

  it('should be created', () => {
    expect(service).toBeTruthy();  // Test if service is created
  });

  it('should get data from API', () => {
    const mockData = [{ id: 1, name: 'Test Address' }];

    // Call the method from the service that makes HTTP requests
    service.getContacts().subscribe(data => {
      expect(data.length).toBe(1);
      expect(data).toEqual(mockData);
    });

    // Mock the API response
    const req = httpMock.expectOne('http://localhost:8080/addressbook/all');
    expect(req.request.method).toBe('GET');
    req.flush(mockData);  // Respond with mock data

    // Verify that there are no outstanding HTTP requests
    httpMock.verify();
  });

  afterEach(() => {
    // Ensure no HTTP requests are pending after each test
    httpMock.verify();
  });
});
