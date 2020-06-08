package com.example.busanapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.busanapp.R;
import java.util.ArrayList;


public class ChecklistFragment extends Fragment {
    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    ListView listView;

    Button btn;

    public ChecklistFragment(){

    }

    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.checklist, container, false);

        //데이터 준비
        items = new ArrayList<String>();
        items.add("Check");
        items.add("Check");
        items.add("Check");

        // 어댑터 생성
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_multiple_choice, items);

        // 어댑터 설정
        listView = view.findViewById(R.id.check_listview);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        btn = (Button) view.findViewById(R.id.btn_add);
        btn = (Button) view.findViewById(R.id.btn_delet);

        btn.setOnClickListener(new Button.OnClickListener() {


            @Override
            public void onClick(View v) {
                EditText ed = view.findViewById(R.id.check_item);
                switch (v.getId()) {
                    case R.id.btn_add:                                 // ADD 버튼 클릭시
                        String text = ed.getText().toString();        // EditText에 입력된 문자열값을 얻기
                        if (text.isEmpty()) {                        // 입력된 text 문자열이 비어있지 않으면
                            items.add(text);                          // items 리스트에 입력된 문자열 추가
                           ed.setText("");                           // EditText 입력란 초기화
                            adapter.notifyDataSetChanged();           // 리스트 목록 갱신
                        }
                        break;


                    case R.id.btn_delet:                             // DELETE 버튼 클릭시
                        SparseBooleanArray sbArry = listView.getCheckedItemPositions();

                        Log.d("ChecklistFragment", sbArry.toString());
                        if (sbArry.size() != 0) {
                            for (int i = listView.getCount() - 1; i >= 0; i--) {
                                if (sbArry.get(i)) {
                                    items.remove(i);
                                }

                            }
                            listView.clearChoices();
                            adapter.notifyDataSetChanged();
                        }
                        break;

                }
            }
        });
        return view;
    }

}
