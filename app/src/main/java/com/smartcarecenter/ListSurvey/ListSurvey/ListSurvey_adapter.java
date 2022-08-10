/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 *  androidx.recyclerview.widget.RecyclerView
 *  androidx.recyclerview.widget.RecyclerView$Adapter
 *  androidx.recyclerview.widget.RecyclerView$ViewHolder
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.ArrayList
 *  java.util.Date
 *  java.util.Locale
 */
package com.smartcarecenter.ListSurvey.ListSurvey;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.smartcarecenter.Add_Foc_request.Add_foc_req_item;
import com.smartcarecenter.R;
import com.smartcarecenter.SurveyActivity;

import org.jsoup.select.Evaluator;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.smartcarecenter.AddDetailFoc.myCustomArray;
import static com.smartcarecenter.SurveyActivity.AnswersArray;
import static com.smartcarecenter.SurveyActivity.MrecylerSurvey;
import static com.smartcarecenter.SurveyActivity.listformreq;
import static java.sql.Types.NULL;

public class ListSurvey_adapter
extends RecyclerView.Adapter<ListSurvey_adapter.Myviewholder> {
    public static ArrayList<ListSurvey_tem> listsurvey;
    public static JsonArray listanwermulti;
    public  static ArrayList<ListSurvey_tem> imglist;
    public  static ArrayList<ListSurveyAnswer_tem> imglist3;
    public  static ListSurveyAnswer_tem imglist2;
    public static ArrayList<AnswerSurvey_tem> setanswer ;
    public static ArrayList<AnswerSurvey_tem> listAnswer = new ArrayList<AnswerSurvey_tem>();
    public  static AnswerSurvey_tem Answers;
    private LinearLayoutManager linearLayoutManager;
    ListSurveyAnswer_adapter imgAdapter;
    ListSurvey_tem modelqty;
    Context context;
    ImageView mimgpopup;
    int posia = 0;
    int posib = 0;
    int posic = 0;
    int posid = 0;
    int posie = 0;
    int posif = 0;
    int posig = 0;
    double subharga = 0.0;
    public static int totalqty = 0;
    public static double totalprice = 0.0;
    boolean required = true;
    boolean hidenum = false;
    public ListSurvey_adapter(Context context, ArrayList<ListSurvey_tem> ListSurvey) {
        this.context = context;
        this.listsurvey = ListSurvey;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.item_survey,
                viewGroup, false));

    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        //setlayout recyler
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        myviewholder.mmultiplerecyler.setLayoutManager(linearLayoutManager);
        myviewholder.mmultiplerecyler.setHasFixedSize(true);
        int liat = listsurvey.get(i).getPosition();
        if (listsurvey.get(i).getQuestionType().equals("Rating")){
            myviewholder.mratinglayout.setVisibility(View.VISIBLE);
//            myviewholder.mgroupname.setVisibility(View.GONE);
            myviewholder.mhidenumber.setVisibility(View.GONE);
            myviewholder.manswerhide.setVisibility(View.GONE);
            myviewholder.mratingpertanyaan.setText(listsurvey.get(i).getQuestion());
            hidenum = true;

           

        }else {
            myviewholder.mratinglayout.setVisibility(View.GONE);
//            myviewholder.mgroupname.setVisibility(View.VISIBLE);
            myviewholder.mhidenumber.setVisibility(View.VISIBLE);
            myviewholder.manswerhide.setVisibility(View.VISIBLE);
        }
        if (listsurvey.get(i).isShowOptionalCommentTextbox()){
            myviewholder.mlaynotedes.setVisibility(View.VISIBLE);
        }else {
            myviewholder.mlaynotedes.setVisibility(View.GONE);
        }
//        if (liat==listsurvey.size()){
//            Log.e("liat", i + " :posisi: " + String.valueOf(liat));
//            MrecylerSurvey.setNestedScrollingEnabled(false);
//        }else {
//            Log.e("liat", i + " :posisi: " + String.valueOf(liat));
//            MrecylerSurvey.setNestedScrollingEnabled(true);
//        }


//        imglist = new ArrayList<ListSurveyAnswer_tem>();
//        ////load
//        for(int x=0;x<listformreq.size();x++){}
//            JsonObject movie = listformreq.get(i).getAsJsonObject();
//            if (movie.get("Answers").toString().equals("null")){
//
//            }else {
//                listanwermulti = movie.getAsJsonArray("Answers");
//
//            }
//
//
////        }
//        Gson gson2 = new Gson();
//        Type listType2 = new TypeToken<ArrayList<ListSurveyAnswer_tem>>() {
//        }.getType();
//
//        imglist = gson2.fromJson(listanwermulti.toString(),listType2);
//        if(imglist!=null && imglist.size()!=0){
//            if (imgAdapter!=null){
//
//            }
//            imgAdapter = new ListSurveyAnswer_adapter(context, imglist);
//            myviewholder.mmultiplerecyler.setAdapter(imgAdapter);
//        }

//            imgAdapter = new ListSurveyAnswer_adapter(context, listsurvey);
//            myviewholder.mmultiplerecyler.setAdapter(imgAdapter);


        required = listsurvey.get(i).isOptional();
        if (!required){
            myviewholder.mrequired.setVisibility(View.VISIBLE);
        }else {
            myviewholder.mrequired.setVisibility(View.GONE);
        }

        myviewholder.mnosur.setText(String.valueOf(i+1)+".");
       if (hidenum){
           myviewholder.mnosur.setVisibility(View.GONE);
       }else {
           myviewholder.mnosur.setVisibility(View.VISIBLE);
       }
        myviewholder.mtitleAnswer.setText(listsurvey.get(i).getQuestion());

        if (listsurvey.get(i).getGroupCd().equals("A")){
            int posi1 = listsurvey.get(i).getPosition()-1;
            posia +=1;
            Log.d("posii",String.valueOf(posia));
            if (posia>1){
                listsurvey.get(posi1).setGroupName("");
            }
        }
        if (listsurvey.get(i).getGroupCd().equals("B")){
            int posi1 = listsurvey.get(i).getPosition()-1;
            posib +=1;
            Log.d("posii",String.valueOf(posib));
            if (posib>1){
                listsurvey.get(posi1).setGroupName("");
            }
        }
        if (listsurvey.get(i).getGroupCd().equals("C")){
            int posi1 = listsurvey.get(i).getPosition()-1;
            posic +=1;
            Log.d("posii",String.valueOf(posic));
            if (posic>1){
                listsurvey.get(posi1).setGroupName("");
            }
        }
        if (listsurvey.get(i).getGroupCd().equals("D")){
            int posi1 = listsurvey.get(i).getPosition()-1;
            posid +=1;
            Log.d("posii",String.valueOf(posid));
            if (posid>1){
                listsurvey.get(posi1).setGroupName("");
            }
        }
        if (listsurvey.get(i).getGroupCd().equals("E")){
            int posi1 = listsurvey.get(i).getPosition()-1;
            posie +=1;
            Log.d("posii",String.valueOf(posie));
            if (posie>1){
                listsurvey.get(posi1).setGroupName("");
            }
        }
        if (listsurvey.get(i).getGroupCd().equals("F")){
            int posi1 = listsurvey.get(i).getPosition()-1;
            posif +=1;
            Log.d("posii",String.valueOf(posif));
            if (posif>1){
                listsurvey.get(posi1).setGroupName("");
            }
        }
        if (listsurvey.get(i).getGroupCd().equals("G")){
            int posi1 = listsurvey.get(i).getPosition()-1;
            posig +=1;
            Log.d("posii",String.valueOf(posig));
            if (posig>1){
                listsurvey.get(posi1).setGroupName("");
            }
        }
        if (listsurvey.get(i).getGroupName().equals("")){
            myviewholder.mgroupname.setVisibility(View.GONE);

        }else {
            myviewholder.mgroupname.setText(listsurvey.get(i).getGroupName());
        }


        if (listsurvey.get(i).getQuestionType().equals("Text")){
            myviewholder.mansgr.setVisibility(View.GONE);
//            myviewholder.manstext.setVisibility(View.VISIBLE);
            myviewholder.manstext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(listsurvey.get(i).getMaxTextLength())});
            myviewholder.manswerhide.setVisibility(View.GONE);
            myviewholder.mnoteoptional.setVisibility(View.GONE);
        }else {
            myviewholder.mansgr.setVisibility(View.VISIBLE);
//            myviewholder.manstext.setVisibility(View.GONE);
            if (listsurvey.get(i).getQuestionType().equals("Rating")){
                myviewholder.manswerhide.setVisibility(View.GONE);
            }else {
                myviewholder.manswerhide.setVisibility(View.VISIBLE);
            }
            myviewholder.mnoteoptional.setVisibility(View.VISIBLE);
            String ada = "";
            if (listsurvey.get(i).getAnswers()!=null){
                ada = String.valueOf(listsurvey.get(i).getAnswers().size());
                if (listsurvey.get(i).getAnswers().size()==2){

                    myviewholder.mans1.setText(listsurvey.get(i).getAnswers().get(0).getAnswer());
                    myviewholder.mans2.setText(listsurvey.get(i).getAnswers().get(1).getAnswer());
                }
                if (listsurvey.get(i).getAnswers().size()==3){
                    myviewholder.mans1.setText(listsurvey.get(i).getAnswers().get(0).getAnswer());
                    myviewholder.mans2.setText(listsurvey.get(i).getAnswers().get(1).getAnswer());
                    myviewholder.mans3.setText(listsurvey.get(i).getAnswers().get(2).getAnswer());
                }
                if (listsurvey.get(i).getAnswers().size()==4){
                    myviewholder.mans1.setText(listsurvey.get(i).getAnswers().get(0).getAnswer());
                    myviewholder.mans2.setText(listsurvey.get(i).getAnswers().get(1).getAnswer());
                    myviewholder.mans3.setText(listsurvey.get(i).getAnswers().get(2).getAnswer());
                    myviewholder.mans4.setText(listsurvey.get(i).getAnswers().get(3).getAnswer());
                }
                if (listsurvey.get(i).getAnswers().size()==5){
                    myviewholder.mans1.setText(listsurvey.get(i).getAnswers().get(0).getAnswer());
                    myviewholder.mans2.setText(listsurvey.get(i).getAnswers().get(1).getAnswer());
                    myviewholder.mans3.setText(listsurvey.get(i).getAnswers().get(2).getAnswer());
                    myviewholder.mans4.setText(listsurvey.get(i).getAnswers().get(3).getAnswer());
                    myviewholder.mans5.setText(listsurvey.get(i).getAnswers().get(4).getAnswer());
                }
                if (listsurvey.get(i).getAnswers().size()==6){
                    myviewholder.mans1.setText(listsurvey.get(i).getAnswers().get(0).getAnswer());
                    myviewholder.mans2.setText(listsurvey.get(i).getAnswers().get(1).getAnswer());
                    myviewholder.mans3.setText(listsurvey.get(i).getAnswers().get(2).getAnswer());
                    myviewholder.mans4.setText(listsurvey.get(i).getAnswers().get(3).getAnswer());
                    myviewholder.mans5.setText(listsurvey.get(i).getAnswers().get(4).getAnswer());
                    myviewholder.mans6.setText(listsurvey.get(i).getAnswers().get(5).getAnswer());
                }
                if (listsurvey.get(i).getAnswers().size()==7){
                    myviewholder.mans1.setText(listsurvey.get(i).getAnswers().get(0).getAnswer());
                    myviewholder.mans2.setText(listsurvey.get(i).getAnswers().get(1).getAnswer());
                    myviewholder.mans3.setText(listsurvey.get(i).getAnswers().get(2).getAnswer());
                    myviewholder.mans4.setText(listsurvey.get(i).getAnswers().get(3).getAnswer());
                    myviewholder.mans5.setText(listsurvey.get(i).getAnswers().get(4).getAnswer());
                    myviewholder.mans6.setText(listsurvey.get(i).getAnswers().get(5).getAnswer());
                    myviewholder.mans7.setText(listsurvey.get(i).getAnswers().get(6).getAnswer());
                }
                if (listsurvey.get(i).getAnswers().size()==8){
                    myviewholder.mans1.setText(listsurvey.get(i).getAnswers().get(0).getAnswer());
                    myviewholder.mans2.setText(listsurvey.get(i).getAnswers().get(1).getAnswer());
                    myviewholder.mans3.setText(listsurvey.get(i).getAnswers().get(2).getAnswer());
                    myviewholder.mans4.setText(listsurvey.get(i).getAnswers().get(3).getAnswer());
                    myviewholder.mans5.setText(listsurvey.get(i).getAnswers().get(4).getAnswer());
                    myviewholder.mans6.setText(listsurvey.get(i).getAnswers().get(5).getAnswer());
                    myviewholder.mans7.setText(listsurvey.get(i).getAnswers().get(6).getAnswer());
                    myviewholder.mans8.setText(listsurvey.get(i).getAnswers().get(7).getAnswer());
                }
                if (listsurvey.get(i).getAnswers().size()==9){
                    myviewholder.mans1.setText(listsurvey.get(i).getAnswers().get(0).getAnswer());
                    myviewholder.mans2.setText(listsurvey.get(i).getAnswers().get(1).getAnswer());
                    myviewholder.mans3.setText(listsurvey.get(i).getAnswers().get(2).getAnswer());
                    myviewholder.mans4.setText(listsurvey.get(i).getAnswers().get(3).getAnswer());
                    myviewholder.mans5.setText(listsurvey.get(i).getAnswers().get(4).getAnswer());
                    myviewholder.mans6.setText(listsurvey.get(i).getAnswers().get(5).getAnswer());
                    myviewholder.mans7.setText(listsurvey.get(i).getAnswers().get(6).getAnswer());
                    myviewholder.mans8.setText(listsurvey.get(i).getAnswers().get(7).getAnswer());
                    myviewholder.mans9.setText(listsurvey.get(i).getAnswers().get(8).getAnswer());
                }
                if (listsurvey.get(i).getAnswers().size()==10){
                    myviewholder.mans1.setText(listsurvey.get(i).getAnswers().get(0).getAnswer());
                    myviewholder.mans2.setText(listsurvey.get(i).getAnswers().get(1).getAnswer());
                    myviewholder.mans3.setText(listsurvey.get(i).getAnswers().get(2).getAnswer());
                    myviewholder.mans4.setText(listsurvey.get(i).getAnswers().get(3).getAnswer());
                    myviewholder.mans5.setText(listsurvey.get(i).getAnswers().get(4).getAnswer());
                    myviewholder.mans6.setText(listsurvey.get(i).getAnswers().get(5).getAnswer());
                    myviewholder.mans7.setText(listsurvey.get(i).getAnswers().get(6).getAnswer());
                    myviewholder.mans8.setText(listsurvey.get(i).getAnswers().get(7).getAnswer());
                    myviewholder.mans9.setText(listsurvey.get(i).getAnswers().get(8).getAnswer());
                    myviewholder.mans10.setText(listsurvey.get(i).getAnswers().get(9).getAnswer());
                }
                if (listsurvey.get(i).getAnswers().size()==11){
                    myviewholder.mans1.setText(listsurvey.get(i).getAnswers().get(0).getAnswer());
                    myviewholder.mans2.setText(listsurvey.get(i).getAnswers().get(1).getAnswer());
                    myviewholder.mans3.setText(listsurvey.get(i).getAnswers().get(2).getAnswer());
                    myviewholder.mans4.setText(listsurvey.get(i).getAnswers().get(3).getAnswer());
                    myviewholder.mans5.setText(listsurvey.get(i).getAnswers().get(4).getAnswer());
                    myviewholder.mans6.setText(listsurvey.get(i).getAnswers().get(5).getAnswer());
                    myviewholder.mans7.setText(listsurvey.get(i).getAnswers().get(6).getAnswer());
                    myviewholder.mans8.setText(listsurvey.get(i).getAnswers().get(7).getAnswer());
                    myviewholder.mans9.setText(listsurvey.get(i).getAnswers().get(8).getAnswer());
                    myviewholder.mans10.setText(listsurvey.get(i).getAnswers().get(9).getAnswer());
                    myviewholder.mans10.setText(listsurvey.get(i).getAnswers().get(10).getAnswer());
                }
                if (listsurvey.get(i).getAnswers().size()==12){
                    myviewholder.mans1.setText(listsurvey.get(i).getAnswers().get(0).getAnswer());
                    myviewholder.mans2.setText(listsurvey.get(i).getAnswers().get(1).getAnswer());
                    myviewholder.mans3.setText(listsurvey.get(i).getAnswers().get(2).getAnswer());
                    myviewholder.mans4.setText(listsurvey.get(i).getAnswers().get(3).getAnswer());
                    myviewholder.mans5.setText(listsurvey.get(i).getAnswers().get(4).getAnswer());
                    myviewholder.mans6.setText(listsurvey.get(i).getAnswers().get(5).getAnswer());
                    myviewholder.mans7.setText(listsurvey.get(i).getAnswers().get(6).getAnswer());
                    myviewholder.mans8.setText(listsurvey.get(i).getAnswers().get(7).getAnswer());
                    myviewholder.mans9.setText(listsurvey.get(i).getAnswers().get(8).getAnswer());
                    myviewholder.mans10.setText(listsurvey.get(i).getAnswers().get(9).getAnswer());
                    myviewholder.mans10.setText(listsurvey.get(i).getAnswers().get(10).getAnswer());
                    myviewholder.mans10.setText(listsurvey.get(i).getAnswers().get(11).getAnswer());
                }
            }
            Log.d("test", ada);


            if (myviewholder.mans1.getText().toString().equals(""))
            {
                myviewholder.mans1.setVisibility(View.GONE);
            }else {
                myviewholder.mans1.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans2.getText().toString().equals(""))
            {
                myviewholder.mans2.setVisibility(View.GONE);
            }else {
                myviewholder.mans2.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans3.getText().toString().equals(""))
            {
                myviewholder.mans3.setVisibility(View.GONE);
            }else {
                myviewholder.mans3.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans4.getText().toString().equals(""))
            {
                myviewholder.mans4.setVisibility(View.GONE);
            }else {
                myviewholder.mans4.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans5.getText().toString().equals(""))
            {
                myviewholder.mans5.setVisibility(View.GONE);
            }else {
                myviewholder.mans5.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans6.getText().toString().equals(""))
            {
                myviewholder.mans6.setVisibility(View.GONE);
            }else {
                myviewholder.mans6.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans7.getText().toString().equals(""))
            {
                myviewholder.mans7.setVisibility(View.GONE);
            }else {
                myviewholder.mans7.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans8.getText().toString().equals(""))
            {
                myviewholder.mans8.setVisibility(View.GONE);
            }else {
                myviewholder.mans8.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans9.getText().toString().equals(""))
            {
                myviewholder.mans9.setVisibility(View.GONE);
            }else {
                myviewholder.mans9.setVisibility(View.VISIBLE);
            }
            if (myviewholder.mans10.getText().toString().equals(""))
            {
                myviewholder.mans10.setVisibility(View.GONE);
            }else {
                myviewholder.mans10.setVisibility(View.VISIBLE);
            }
//            RadioGroup groupy = new RadioGroup(context);
            myviewholder.mansgr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int radioBtnID = group.getCheckedRadioButtonId();
                    View radioB = group.findViewById(radioBtnID);
                    int position = group.indexOfChild(radioB);
                    listAnswer.get(i).setAnswerPosition(listsurvey.get(i).getAnswers().get(position).getAnswerPosition());
                    Log.e("POSISI", i + " :onCheckedChanged: " + String.valueOf(listsurvey.get(i).getAnswers().get(position).getAnswerPosition()));
                    Gson gson = new GsonBuilder().create();
                    AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
                }
            });
        }

        Answers = new AnswerSurvey_tem();
        if (listsurvey.get(i).getQuestionType().equals("Rating")){
            Answers.setAnswerPosition(99999);
        }else {
            Answers.setAnswerPosition(0);
        }

        Answers.setQuestionPosition(listsurvey.get(i).getPosition());
        Answers.setAnswerText("");
        Answers.setOptional(listsurvey.get(i).isOptional());
        Answers.setQuestionType(listsurvey.get(i).getQuestionType());
        listAnswer.add(Answers);
        myviewholder.manstext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (myviewholder.manstext.length()==0){
                    listAnswer.get(i).setAnswerText("");
                }else {
                    listAnswer.get(i).setAnswerText(myviewholder.manstext.getText().toString());
//                    Toast.makeText(context, listAnswer.toString(),Toast.LENGTH_LONG).show();
                }
                Gson gson = new GsonBuilder().create();
                AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
//                Toast.makeText(context, AnswersArray.toString(),Toast.LENGTH_LONG).show();
            }

        });
        ///mrating
        myviewholder.mcircle0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myviewholder.mcheck0.setVisibility(View.VISIBLE);
                myviewholder.mcheck1.setVisibility(View.GONE);
                myviewholder.mcheck2.setVisibility(View.GONE);
                myviewholder.mcheck3.setVisibility(View.GONE);
                myviewholder.mcheck4.setVisibility(View.GONE);
                myviewholder.mcheck5.setVisibility(View.GONE);
                myviewholder.mcheck6.setVisibility(View.GONE);
                myviewholder.mcheck7.setVisibility(View.GONE);
                myviewholder.mcheck8.setVisibility(View.GONE);
                myviewholder.mcheck9.setVisibility(View.GONE);
                myviewholder.mcheck10.setVisibility(View.GONE);



                listAnswer.get(i).setAnswerPosition(0);
                Log.e("POSISI", i + " :onCheckedChanged: " + String.valueOf(0));
                Gson gson = new GsonBuilder().create();
                AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
            }
        });


        myviewholder.mcircle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myviewholder.mcheck0.setVisibility(View.GONE);
                myviewholder.mcheck1.setVisibility(View.VISIBLE);
                myviewholder.mcheck2.setVisibility(View.GONE);
                myviewholder.mcheck3.setVisibility(View.GONE);
                myviewholder.mcheck4.setVisibility(View.GONE);
                myviewholder.mcheck5.setVisibility(View.GONE);
                myviewholder.mcheck6.setVisibility(View.GONE);
                myviewholder.mcheck7.setVisibility(View.GONE);
                myviewholder.mcheck8.setVisibility(View.GONE);
                myviewholder.mcheck9.setVisibility(View.GONE);
                myviewholder.mcheck10.setVisibility(View.GONE);

                listAnswer.get(i).setAnswerPosition(1);
                Log.e("POSISI", i + " :onCheckedChanged: " + String.valueOf(1));
                Gson gson = new GsonBuilder().create();
                AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
            }
        });
        myviewholder.mcircle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myviewholder.mcheck0.setVisibility(View.GONE);
                myviewholder.mcheck1.setVisibility(View.GONE);
                myviewholder.mcheck2.setVisibility(View.VISIBLE);
                myviewholder.mcheck3.setVisibility(View.GONE);
                myviewholder.mcheck4.setVisibility(View.GONE);
                myviewholder.mcheck5.setVisibility(View.GONE);
                myviewholder.mcheck6.setVisibility(View.GONE);
                myviewholder.mcheck7.setVisibility(View.GONE);
                myviewholder.mcheck8.setVisibility(View.GONE);
                myviewholder.mcheck9.setVisibility(View.GONE);
                myviewholder.mcheck10.setVisibility(View.GONE);

                listAnswer.get(i).setAnswerPosition(2);
                Log.e("POSISI", i + " :onCheckedChanged: " + String.valueOf(2));
                Gson gson = new GsonBuilder().create();
                AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
            }
        });
        myviewholder.mcircle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myviewholder.mcheck0.setVisibility(View.GONE);
                myviewholder.mcheck1.setVisibility(View.GONE);
                myviewholder.mcheck2.setVisibility(View.GONE);
                myviewholder.mcheck3.setVisibility(View.VISIBLE);
                myviewholder.mcheck4.setVisibility(View.GONE);
                myviewholder.mcheck5.setVisibility(View.GONE);
                myviewholder.mcheck6.setVisibility(View.GONE);
                myviewholder.mcheck7.setVisibility(View.GONE);
                myviewholder.mcheck8.setVisibility(View.GONE);
                myviewholder.mcheck9.setVisibility(View.GONE);
                myviewholder.mcheck10.setVisibility(View.GONE);

                listAnswer.get(i).setAnswerPosition(3);
                Log.e("POSISI", i + " :onCheckedChanged: " + String.valueOf(3));
                Gson gson = new GsonBuilder().create();
                AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
            }
        });
        myviewholder.mcircle4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myviewholder.mcheck0.setVisibility(View.GONE);
                myviewholder.mcheck1.setVisibility(View.GONE);
                myviewholder.mcheck2.setVisibility(View.GONE);
                myviewholder.mcheck3.setVisibility(View.GONE);
                myviewholder.mcheck4.setVisibility(View.VISIBLE);
                myviewholder.mcheck5.setVisibility(View.GONE);
                myviewholder.mcheck6.setVisibility(View.GONE);
                myviewholder.mcheck7.setVisibility(View.GONE);
                myviewholder.mcheck8.setVisibility(View.GONE);
                myviewholder.mcheck9.setVisibility(View.GONE);
                myviewholder.mcheck10.setVisibility(View.GONE);

                listAnswer.get(i).setAnswerPosition(4);
                Log.e("POSISI", i + " :onCheckedChanged: " + String.valueOf(4));
                Gson gson = new GsonBuilder().create();
                AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
            }
        });
        myviewholder.mcircle5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myviewholder.mcheck0.setVisibility(View.GONE);
                myviewholder.mcheck1.setVisibility(View.GONE);
                myviewholder.mcheck2.setVisibility(View.GONE);
                myviewholder.mcheck3.setVisibility(View.GONE);
                myviewholder.mcheck4.setVisibility(View.GONE);
                myviewholder.mcheck5.setVisibility(View.VISIBLE);
                myviewholder.mcheck6.setVisibility(View.GONE);
                myviewholder.mcheck7.setVisibility(View.GONE);
                myviewholder.mcheck8.setVisibility(View.GONE);
                myviewholder.mcheck9.setVisibility(View.GONE);
                myviewholder.mcheck10.setVisibility(View.GONE);

                listAnswer.get(i).setAnswerPosition(5);
                Log.e("POSISI", i + " :onCheckedChanged: " + String.valueOf(5));
                Gson gson = new GsonBuilder().create();
                AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
            }
        });
        myviewholder.mcircle6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myviewholder.mcheck0.setVisibility(View.GONE);
                myviewholder.mcheck1.setVisibility(View.GONE);
                myviewholder.mcheck2.setVisibility(View.GONE);
                myviewholder.mcheck3.setVisibility(View.GONE);
                myviewholder.mcheck4.setVisibility(View.GONE);
                myviewholder.mcheck5.setVisibility(View.GONE);
                myviewholder.mcheck6.setVisibility(View.VISIBLE);
                myviewholder.mcheck7.setVisibility(View.GONE);
                myviewholder.mcheck8.setVisibility(View.GONE);
                myviewholder.mcheck9.setVisibility(View.GONE);
                myviewholder.mcheck10.setVisibility(View.GONE);

                listAnswer.get(i).setAnswerPosition(6);
                Log.e("POSISI", i + " :onCheckedChanged: " + String.valueOf(6));
                Gson gson = new GsonBuilder().create();
                AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
            }
        });
        myviewholder.mcircle7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myviewholder.mcheck0.setVisibility(View.GONE);
                myviewholder.mcheck1.setVisibility(View.GONE);
                myviewholder.mcheck2.setVisibility(View.GONE);
                myviewholder.mcheck3.setVisibility(View.GONE);
                myviewholder.mcheck4.setVisibility(View.GONE);
                myviewholder.mcheck5.setVisibility(View.GONE);
                myviewholder.mcheck6.setVisibility(View.GONE);
                myviewholder.mcheck7.setVisibility(View.VISIBLE);
                myviewholder.mcheck8.setVisibility(View.GONE);
                myviewholder.mcheck9.setVisibility(View.GONE);
                myviewholder.mcheck10.setVisibility(View.GONE);

                listAnswer.get(i).setAnswerPosition(7);
                Log.e("POSISI", i + " :onCheckedChanged: " + String.valueOf(7));
                Gson gson = new GsonBuilder().create();
                AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
            }
        });
        myviewholder.mcircle8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myviewholder.mcheck0.setVisibility(View.GONE);
                myviewholder.mcheck1.setVisibility(View.GONE);
                myviewholder.mcheck2.setVisibility(View.GONE);
                myviewholder.mcheck3.setVisibility(View.GONE);
                myviewholder.mcheck4.setVisibility(View.GONE);
                myviewholder.mcheck5.setVisibility(View.GONE);
                myviewholder.mcheck6.setVisibility(View.GONE);
                myviewholder.mcheck7.setVisibility(View.GONE);
                myviewholder.mcheck8.setVisibility(View.VISIBLE);
                myviewholder.mcheck9.setVisibility(View.GONE);
                myviewholder.mcheck10.setVisibility(View.GONE);

                listAnswer.get(i).setAnswerPosition(8);
                Log.e("POSISI", i + " :onCheckedChanged: " + String.valueOf(8));
                Gson gson = new GsonBuilder().create();
                AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
            }
        });
        myviewholder.mcircle9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myviewholder.mcheck0.setVisibility(View.GONE);
                myviewholder.mcheck1.setVisibility(View.GONE);
                myviewholder.mcheck2.setVisibility(View.GONE);
                myviewholder.mcheck3.setVisibility(View.GONE);
                myviewholder.mcheck4.setVisibility(View.GONE);
                myviewholder.mcheck5.setVisibility(View.GONE);
                myviewholder.mcheck6.setVisibility(View.GONE);
                myviewholder.mcheck7.setVisibility(View.GONE);
                myviewholder.mcheck8.setVisibility(View.GONE);
                myviewholder.mcheck9.setVisibility(View.VISIBLE);
                myviewholder.mcheck10.setVisibility(View.GONE);

                listAnswer.get(i).setAnswerPosition(9);
                Log.e("POSISI", i + " :onCheckedChanged: " + String.valueOf(9));
                Gson gson = new GsonBuilder().create();
                AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
            }
        });
        myviewholder.mcircle10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myviewholder.mcheck0.setVisibility(View.GONE);
                myviewholder.mcheck1.setVisibility(View.GONE);
                myviewholder.mcheck2.setVisibility(View.GONE);
                myviewholder.mcheck3.setVisibility(View.GONE);
                myviewholder.mcheck4.setVisibility(View.GONE);
                myviewholder.mcheck5.setVisibility(View.GONE);
                myviewholder.mcheck6.setVisibility(View.GONE);
                myviewholder.mcheck7.setVisibility(View.GONE);
                myviewholder.mcheck8.setVisibility(View.GONE);
                myviewholder.mcheck9.setVisibility(View.GONE);
                myviewholder.mcheck10.setVisibility(View.VISIBLE);

                listAnswer.get(i).setAnswerPosition(10);
                Log.e("POSISI", i + " :onCheckedChanged: " + String.valueOf(10));
                Gson gson = new GsonBuilder().create();
                AnswersArray = gson.toJsonTree(listAnswer).getAsJsonArray();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 
                listsurvey.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{

        TextView mnosur, mtitleAnswer,mrequired, mgroupname,mratingpertanyaan,mnoteoptional;
        EditText manstext;
        RecyclerView mmultiplerecyler;
        LinearLayout mratinglayout,mlaynotedes,manswerhide,mhidenumber,mcheck10,mcheck9,mcheck8,mcheck7,mcheck6,mcheck5,mcheck4,mcheck3,mcheck2,mcheck1,mcheck0;
        RelativeLayout mcircle0,mcircle1,mcircle2,mcircle3,mcircle4,mcircle5,mcircle6,mcircle7,mcircle8,mcircle9,mcircle10;
        private RadioGroup mansgr;
        private RadioButton mans1, mans2, mans3,mans4,mans5,mans6,mans7,mans8,mans9,mans10 ;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);

            mgroupname = itemView.findViewById(R.id.groupname);
            mnosur = itemView.findViewById(R.id.noSurvey);
            mtitleAnswer = itemView.findViewById(R.id.titleAnswer);
            manstext = itemView.findViewById(R.id.anstext);
            mrequired = itemView.findViewById(R.id.requiredanswer);
            mmultiplerecyler = itemView.findViewById(R.id.multiplerecyler);
            mans1 = itemView.findViewById(R.id.ans1);
            mans2 = itemView.findViewById(R.id.ans2);
            mans3 = itemView.findViewById(R.id.ans3);
            mans4 = itemView.findViewById(R.id.ans4);
            mans5 = itemView.findViewById(R.id.ans5);
            mans6 = itemView.findViewById(R.id.ans6);
            mans7 = itemView.findViewById(R.id.ans7);
            mans8 = itemView.findViewById(R.id.ans8);
            mans9 = itemView.findViewById(R.id.ans9);
            mans10 = itemView.findViewById(R.id.ans10);
            mansgr = itemView.findViewById(R.id.ansgr);
            manswerhide = itemView.findViewById(R.id.answerhide);
            mhidenumber = itemView.findViewById(R.id.hidenumber);
            mratinglayout = itemView.findViewById(R.id.rartinglayout);
            mlaynotedes = itemView.findViewById(R.id.laynotedes);
            mcheck10 = itemView.findViewById(R.id.check10);
            mcheck9 = itemView.findViewById(R.id.check9);
            mcheck8 = itemView.findViewById(R.id.check8);
            mcheck7 = itemView.findViewById(R.id.check7);
            mcheck6 = itemView.findViewById(R.id.check6);
            mcheck5 = itemView.findViewById(R.id.check5);
            mcheck4 = itemView.findViewById(R.id.check4);
            mcheck3 = itemView.findViewById(R.id.check3);
            mcheck2 = itemView.findViewById(R.id.check2);
            mcheck1 = itemView.findViewById(R.id.check1);
            mcheck0 = itemView.findViewById(R.id.check0);

            mcircle0 = itemView.findViewById(R.id.circle0);
            mcircle1 = itemView.findViewById(R.id.circle1);
            mcircle2 = itemView.findViewById(R.id.circle2);
            mcircle3 = itemView.findViewById(R.id.circle3);
            mcircle4 = itemView.findViewById(R.id.circle4);
            mcircle5 = itemView.findViewById(R.id.circle5);
            mcircle6 = itemView.findViewById(R.id.circle6);
            mcircle7 = itemView.findViewById(R.id.circle7);
            mcircle8 = itemView.findViewById(R.id.circle8);
            mcircle9 = itemView.findViewById(R.id.circle9);
            mcircle10 = itemView.findViewById(R.id.circle10);
            mratingpertanyaan = itemView.findViewById(R.id.ratingpertanyaan);
            mnoteoptional = itemView.findViewById(R.id.noteoptional);


        }
    }
}

