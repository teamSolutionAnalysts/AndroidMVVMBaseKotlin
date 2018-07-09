package com.sa.baseproject.utils

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.sa.baseproject.appview.MainActivity
import com.sa.baseproject.utils.PermissionUtils.PermissionGranted

/**
 * Created by sa on 05/04/17.
 *
 */

class LocationUtils(private val context: Context) : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, PermissionGranted {
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocationRequest: LocationRequest? = null
    var mCurrentLocation: Location? = null
    var latitude: Double = 0.toDouble()
        private set

    var longitude: Double = 0.toDouble()
        private set

    private var builder: LocationSettingsRequest.Builder? = null
    private val permissionUtils: PermissionUtils = (context as MainActivity).permissionUtils!!

    init {
        permissionUtils.setPermissionGranted(this)
        buildGoogleApiClient()
        checkGPS()
        onStart()
    }

    /**
     * Builds a GoogleApiClient. Uses the `#addApi` method to request the
     * LocationServices API.
     */
    @Synchronized private fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        createLocationRequest()
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest!!.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        builder = LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest!!)
        //**************************
        builder!!.setAlwaysShow(true) //this is the key ingredient
        //**************************

    }

    private fun startLocationUpdates() {
        if (!permissionUtils.checkPermission(ACCESS_COARSE_LOCATION) && !permissionUtils.checkPermission(ACCESS_FINE_LOCATION)) {
            if(mGoogleApiClient!=null && mLocationRequest!=null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient, mLocationRequest, this)
            }
        } else {
            permissionUtils.checkPermission(ACCESS_COARSE_LOCATION, Constants.REQUEST_CODE_ASK_PERMISSIONS)
        }
    }

    private fun stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
    }

    private fun onStart() {
        mGoogleApiClient!!.connect()
        if (mGoogleApiClient!!.isConnected) {
            startLocationUpdates()
        }
    }

    fun onResume() {
        if (mGoogleApiClient!!.isConnected) {
            startLocationUpdates()
        }
    }

    fun onPause() {
        if (mGoogleApiClient!!.isConnected) {
            stopLocationUpdates()
        }
    }

    fun onStop() {
        mGoogleApiClient!!.disconnect()
    }

    override fun onConnected(connectionHint: Bundle?) {
        if (mCurrentLocation == null) {
            if (permissionUtils.checkPermission(ACCESS_COARSE_LOCATION) && permissionUtils.checkPermission(ACCESS_FINE_LOCATION)) {
                permissionUtils.checkPermission(ACCESS_COARSE_LOCATION, Constants.REQUEST_CODE_ASK_PERMISSIONS)
                return
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
            getLocation()

        }
        startLocationUpdates()
    }

    override fun onLocationChanged(location: Location) {
        mCurrentLocation = location
        getLocation()
    }

    override fun onConnectionSuspended(cause: Int) {
        mGoogleApiClient!!.connect()
    }

    override fun onConnectionFailed(result: ConnectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.errorCode)
    }

    private fun checkGPS() {
        if (mGoogleApiClient != null) {
            val result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder!!.build())
            result.setResultCallback { locResult ->
                val status = locResult.status
                //                    final LocationSettingsStates state = locResult.getLocationSettingsStates();
                when (status.statusCode) {
                    LocationSettingsStatusCodes.SUCCESS -> createLocationRequest()
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        status.startResolutionForResult(
                                context as Activity, 1000)
                    } catch (e: IntentSender.SendIntentException) {
                        e.printStackTrace()
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    }
                }
            }
        }
    }

    private fun getLocation() {
        if (mCurrentLocation != null) {
            longitude = mCurrentLocation!!.longitude
            latitude = mCurrentLocation!!.latitude
            if (longitude != 0.0 && latitude != 0.0) {

            }
        }
    }


    override fun onPermissionGranted(requestCode: Int) {
        onStart()
    }

    companion object {

        private val TAG = "location-updates-sample"
        private val UPDATE_INTERVAL_IN_MILLISECONDS = (5 * 1000).toLong()
        private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }
}
