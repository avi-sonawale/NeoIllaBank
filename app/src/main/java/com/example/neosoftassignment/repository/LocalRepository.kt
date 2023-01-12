package com.example.neosoftassignment.repository

import com.example.neosoftassignment.R
import com.example.neosoftassignment.network.CallState
import com.example.neosoftassignment.model.HorizontalAndVerticalListModel
import com.example.neosoftassignment.model.VerticalListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LocalRepository {

    fun fetchDataToLoad(): Flow<CallState<List<HorizontalAndVerticalListModel>>> = flow {
        emit(
            CallState.success(
                arrayListOf(
                    HorizontalAndVerticalListModel(
                        R.drawable.ic_laptop_electronic_banner, arrayListOf(
                            VerticalListModel("Laptop Asus 1", R.drawable.ic_laptop_electronic_list),
                            VerticalListModel("Laptop Asus 2", R.drawable.ic_laptop_electronic_list),
                            VerticalListModel("Laptop Asus 3", R.drawable.ic_laptop_electronic_list),
                            VerticalListModel("Laptop Lenovo 1", R.drawable.ic_laptop_electronic_list),
                            VerticalListModel("Laptop Lenovo 2", R.drawable.ic_laptop_electronic_list),
                            VerticalListModel("Laptop Lenovo 3", R.drawable.ic_laptop_electronic_list),
                            VerticalListModel("Laptop Acer 1", R.drawable.ic_laptop_electronic_list),
                            VerticalListModel("Laptop Acer 2", R.drawable.ic_laptop_electronic_list),
                            VerticalListModel("Laptop Acer 3", R.drawable.ic_laptop_electronic_list),
                        )
                    ),
                    HorizontalAndVerticalListModel(
                        R.drawable.ic_bag_banner, arrayListOf(
                            VerticalListModel("Bag Hp 1", R.drawable.ic_bag_list),
                            VerticalListModel("Bag Hp 2", R.drawable.ic_bag_list),
                            VerticalListModel("Bag Hp 3", R.drawable.ic_bag_list),
                            VerticalListModel("Bag Lenovo 1", R.drawable.ic_bag_list),
                            VerticalListModel("Bag Lenovo 2", R.drawable.ic_bag_list),
                            VerticalListModel("Bag Lenovo 3", R.drawable.ic_bag_list),
                            VerticalListModel("Bag Acer 1", R.drawable.ic_bag_list),
                            VerticalListModel("Bag Acer 2", R.drawable.ic_bag_list),
                            VerticalListModel("Bag Acer 3", R.drawable.ic_bag_list),
                        )
                    ),
                    HorizontalAndVerticalListModel(
                        R.drawable.ic_charger_banner, arrayListOf(
                            VerticalListModel("Charger Mi 1", R.drawable.ic_charger_list),
                            VerticalListModel("Charger Mi 2", R.drawable.ic_charger_list),
                            VerticalListModel("Charger Mi 3", R.drawable.ic_charger_list),
                            VerticalListModel("Charger Oppo 1", R.drawable.ic_charger_list),
                            VerticalListModel("Charger Oppo 2", R.drawable.ic_charger_list),
                            VerticalListModel("Charger Oppo 3", R.drawable.ic_charger_list),
                            VerticalListModel("Charger Vivo 1", R.drawable.ic_charger_list),
                            VerticalListModel("Charger Vivo 2", R.drawable.ic_charger_list),
                            VerticalListModel("Charger Vivo 3", R.drawable.ic_charger_list),
                        )
                    )
                )
            )
        )
    }.flowOn(Dispatchers.IO)
}