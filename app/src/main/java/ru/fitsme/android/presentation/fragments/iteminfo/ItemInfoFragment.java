package ru.fitsme.android.presentation.fragments.iteminfo;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentItemInfoBinding;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;

public class ItemInfoFragment extends Fragment {
    @Inject
    IClothesInteractor clothesInteractor;

    private static final String INDEX_KEY = "indexKey";

    private int index;
    private ItemInfoState.State state;
    private FragmentItemInfoBinding binding;
    private ItemInfoViewModel viewModel;

    public ItemInfoFragment() {
        App.getInstance().getDi().inject(this);
    }

    public static ItemInfoFragment newInstance(int index) {
        ItemInfoFragment fragment = new ItemInfoFragment();

        Bundle args = new Bundle();
        args.putInt(INDEX_KEY, index);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_info, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        index = getArguments().getInt(INDEX_KEY);

        viewModel = ViewModelProviders.of(this,
                new ItemInfoViewModel.Factory(clothesInteractor, index))
                .get(ItemInfoViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }

        viewModel.getItemLiveData()
                .observe(this, this::onItem);
    }

    private void onItem(ItemInfoState itemInfoState) {
        state = itemInfoState.getState();
        switch (state) {
            case ERROR:
                binding.tvIndex.setText("error");
                break;
            case LOADING:
                binding.tvIndex.setText("loading");
                break;
            case OK:
                String url = itemInfoState.getClothesItem()
                        .getPics()
                        .get(0)
                        .getUrl();
//                        .replace("random", "image=");
//                url += itemInfoState.getClothesItem().getId() % 400;
                Glide.with(binding.ivPhoto)
                        .load(url)//TODO:debug
                        .into(binding.ivPhoto);
                binding.tvIndex.setText(index + " index");
                break;
        }
        //TODO: реализовать отображение
    }

    public boolean isActive() {
        return state == ItemInfoState.State.OK;
    }
}
