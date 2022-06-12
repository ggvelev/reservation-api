# ReservationControllerApi

All URIs are relative to *http://localhost:8080*

| Method                                                                   | HTTP request                                 | Description                                              |
|--------------------------------------------------------------------------|----------------------------------------------|----------------------------------------------------------|
| [**createReservation**](ReservationControllerApi.md#createReservation)   | **POST** /api/reservations                   | Request new reservation to be created.                   |
| [**deleteReservation**](ReservationControllerApi.md#deleteReservation)   | **DELETE** /api/reservations/{reservationId} | Deletes a reservation by ID.                             |
| [**getReservationById**](ReservationControllerApi.md#getReservationById) | **GET** /api/reservations/{reservationId}    | Retrieve existing reservation details by reservation ID. |
| [**getReservations**](ReservationControllerApi.md#getReservations)       | **GET** /api/reservations                    | Query for reservations.                                  |
| [**updateReservation**](ReservationControllerApi.md#updateReservation)   | **PUT** /api/reservations/{reservationId}    | Update existing reservation details.                     |

<a name="createReservation"></a>
# **createReservation**
> ReservationInfo createReservation(ReservationRequest)

Request new reservation to be created.

    The newly created resource URI location will be returned in &#39;Location&#39; header.

### Parameters

| Name                   | Type                                                      | Description | Notes |
|------------------------|-----------------------------------------------------------|-------------|-------|
| **ReservationRequest** | [**ReservationRequest**](../Models/ReservationRequest.md) |             |       |

### Return type

[**ReservationInfo**](../Models/ReservationInfo.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="deleteReservation"></a>
# **deleteReservation**
> deleteReservation(reservationId)

Deletes a reservation by ID.

### Parameters

| Name              | Type     | Description | Notes             |
|-------------------|----------|-------------|-------------------|
| **reservationId** | **Long** |             | [default to null] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="getReservationById"></a>
# **getReservationById**
> ReservationInfo getReservationById(reservationId)

Retrieve existing reservation details by reservation ID.

### Parameters

| Name              | Type     | Description | Notes             |
|-------------------|----------|-------------|-------------------|
| **reservationId** | **Long** |             | [default to null] |

### Return type

[**ReservationInfo**](../Models/ReservationInfo.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="getReservations"></a>
# **getReservations**
> List getReservations(name, date, time)

Query for reservations.

    Retrieves all persisted reservations. Optionally search for reservations matching by name, date and time.

### Parameters

| Name     | Type                           | Description | Notes                        |
|----------|--------------------------------|-------------|------------------------------|
| **name** | **String**                     |             | [optional] [default to null] |
| **date** | **date**                       |             | [optional] [default to null] |
| **time** | [**LocalTime**](../Models/.md) |             | [optional] [default to null] |

### Return type

[**List**](../Models/ReservationInfo.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="updateReservation"></a>
# **updateReservation**
> ReservationInfo updateReservation(reservationId, EditReservationRequest)

Update existing reservation details.

    Only non-null values will be taken into account when updating the reservation model

### Parameters

| Name                       | Type                                                              | Description | Notes             |
|----------------------------|-------------------------------------------------------------------|-------------|-------------------|
| **reservationId**          | **Long**                                                          |             | [default to null] |
| **EditReservationRequest** | [**EditReservationRequest**](../Models/EditReservationRequest.md) |             |                   |

### Return type

[**ReservationInfo**](../Models/ReservationInfo.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

