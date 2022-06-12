# Documentation for RESTaurant reservation API

<a name="documentation-for-api-endpoints"></a>
## Documentation for API Endpoints

All URIs are relative to *http://localhost:8080*

| Class                      | Method                                                                        | HTTP request                                 | Description                                              |
|----------------------------|-------------------------------------------------------------------------------|----------------------------------------------|----------------------------------------------------------|
| *AboutControllerApi*       | [**aboutInfo**](Apis/AboutControllerApi.md#aboutinfo)                         | **GET** /api/about                           | Provides brief info about the reservation API server.    |
| *ReservationControllerApi* | [**createReservation**](Apis/ReservationControllerApi.md#createreservation)   | **POST** /api/reservations                   | Request new reservation to be created.                   |
| *ReservationControllerApi* | [**deleteReservation**](Apis/ReservationControllerApi.md#deletereservation)   | **DELETE** /api/reservations/{reservationId} | Deletes a reservation by ID.                             |
| *ReservationControllerApi* | [**getReservationById**](Apis/ReservationControllerApi.md#getreservationbyid) | **GET** /api/reservations/{reservationId}    | Retrieve existing reservation details by reservation ID. |
| *ReservationControllerApi* | [**getReservations**](Apis/ReservationControllerApi.md#getreservations)       | **GET** /api/reservations                    | Query for reservations.                                  |
| *ReservationControllerApi* | [**updateReservation**](Apis/ReservationControllerApi.md#updatereservation)   | **PUT** /api/reservations/{reservationId}    | Update existing reservation details.                     |

<a name="documentation-for-models"></a>
## Documentation for Models

 - [EditReservationRequest](./Models/EditReservationRequest.md)
 - [LocalTime](./Models/LocalTime.md)
 - [ReservationInfo](./Models/ReservationInfo.md)
 - [ReservationRequest](./Models/ReservationRequest.md)


<a name="documentation-for-authorization"></a>
## Documentation for Authorization

<a name="Basic"></a>
### Basic

- **Type**: HTTP basic authentication

