package me.zdnuist.proxy.fragment_new;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;
import java.util.ArrayList;
import java.util.List;
import me.zdnuist.module.fragmentation.BaseSupportFragment;
import me.zdnuist.proxy.R;

public class PlaceHolderFragment extends BaseSupportFragment {

    public static PlaceHolderFragment newInstance() {
        return new PlaceHolderFragment();
    }

    private RecyclerView recyclerView;
    private Animator spruceAnimator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_placeholder_view, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                initSpruce();
            }
        };

        // Mock data objects
        List<String> placeHolderList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            placeHolderList.add("" + i);
        }

        recyclerView.setAdapter(new RecyclerAdapter(placeHolderList));
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (spruceAnimator != null) {
            spruceAnimator.start();
        }
    }

    private void initSpruce() {
        spruceAnimator = new Spruce.SpruceBuilder(recyclerView)
                .sortWith(new DefaultSort(100))
                .animateWith(DefaultAnimations.fadeInAnimator(recyclerView, 800),
                        ObjectAnimator
                            .ofFloat(recyclerView, "translationX", -recyclerView.getWidth(), 0f).setDuration(800))
                .start();
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        List<String> placeholderList;

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            RelativeLayout placeholderView;

            ViewHolder(View itemView) {
                super(itemView);
                placeholderView = (RelativeLayout) itemView.findViewById(R.id.placeholder_view);
                placeholderView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                initSpruce();
            }
        }

        RecyclerAdapter(List<String> placeholderList) {
            this.placeholderList = placeholderList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RelativeLayout view = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_placeholder, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return placeholderList.size();
        }

    }
}