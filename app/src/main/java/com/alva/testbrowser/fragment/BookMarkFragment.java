package com.alva.testbrowser.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alva.testbrowser.Adapter.BookmarkAdapter;
import com.alva.testbrowser.Adapter.HistoryAdapter;
import com.alva.testbrowser.R;
import com.alva.testbrowser.database.Bookmark;
import com.alva.testbrowser.database.MyDataBase;
import com.alva.testbrowser.database.RecordViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookMarkFragment extends Fragment {
    private List<Bookmark> bookmarkList = new ArrayList<>();
    MyDataBase dataBase;
    RecordViewModel recordViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookmark_layout,container,false);

        ListView listView = view.findViewById(R.id.bookmark_view);

        recordViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(RecordViewModel.class);
        recordViewModel.getALLBookmark();
        BookmarkAdapter adapter = new BookmarkAdapter(getActivity(), R.layout.simple_adapter_item,recordViewModel.bookmarkList);
        listView.setAdapter(adapter);

        return view;
    }

}
