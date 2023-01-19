package com.example.androidchallenge.ui.map

import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.androidchallenge.R
import com.example.androidchallenge.databinding.FragmentMapBinding
import com.example.androidchallenge.domain.models.poi.AddressInfo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Converter
import java.io.StringReader


@AndroidEntryPoint
class MapFragment: Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding: FragmentMapBinding get() = _binding!!
    private val viewModel: MapViewModel by viewModels()
    lateinit var map: GoogleMap
    lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.repeatRequest(5, 52.526, 13.415)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment
        mapFragment.getMapAsync(this)

        bottomSheetBehavior = BottomSheetBehavior.from(binding.poiDetail.bottomSheet)

        viewModel.poiItems.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty().not()) {
                binding.progressBar.visibility = View.GONE
                list.forEachIndexed { index, poi ->
                    map.addMarker(MarkerOptions()
                        .position(LatLng(poi.addressInfo?.latitude ?: 0.0, poi.addressInfo?.longitude ?: 0.0)))?.tag = index
                }
                map.setOnMarkerClickListener {
                    showPoiDetail(list[it.tag as Int].addressInfo, list[it.tag as Int].numberOfPoints)
                    return@setOnMarkerClickListener false
                }
            }
        }
        viewModel.networkError.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), requireContext().getString(R.string.network_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPoiDetail(addressInfo: AddressInfo?, numberOfPoints: Int?) {
        binding.poiDetail.chargingTitle.text = String.format(requireContext().getString(R.string.station_title), addressInfo?.title)
        binding.poiDetail.chargingAddress.text = String.format(requireContext().getString(R.string.station_address), addressInfo?.addressLine1)
        binding.poiDetail.chargingCount.isVisible = numberOfPoints != null
        if (numberOfPoints != null) {
            binding.poiDetail.chargingCount.text =
                String.format(requireContext().getString(R.string.charging_number), numberOfPoints)
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMapClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        viewModel.getPoiItems(5, 52.526, 13.415)
        val cameraPosition = CameraPosition.Builder().target(LatLng(52.526, 13.415)).zoom(13f).build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.poiItems.removeObservers(this)
        _binding = null
    }
}