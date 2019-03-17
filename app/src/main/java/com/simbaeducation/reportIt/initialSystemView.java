package com.simbaeducation.reportIt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.simbaeducation.reportIt.Registration.EmailSenderAsync;
import com.simbaeducation.reportIt.Registration.SendMail;
import com.simbaeducation.reportIt.Webservice.ServiceGetData;
import com.simbaeducation.reportIt.Webservice.ServiceLogin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static com.simbaeducation.reportIt.DisableKeyboard.disableSoftInputFromAppearing;
import static java.lang.Integer.parseInt;

public class initialSystemView extends AppCompatActivity {


    private static Button btnShowExp, btntask,btnbSub,btnaddobjectives,btnaddgoals,btnsubmitReport;
    private static TextView  hmeupdate ,taskname ;
    private static EditText fullname ,  edtxtobj,startdate,enddate,edtxtgoals ,edtxtReportDate , edtxtdeadline , edtxtdescrip ,notes ,edtxtstatus,taskdate;
    private static Db_Operations myDb ;
    private static CheckBox cash , ecocash ,bank ;

    private static Spinner spnType,spndaytype ;
    private static Cursor cursor;
    private static ProgressBar progress;
    private static final int CHOOSE_FILE_REQUESTCODE = 8777;
    private static ArrayList<LeaveTaken> lvlist;
    private static homeAdapter reportadapter;
    private static LinearLayout layoutgoal,layoutobj,linearTasks;
    private static final int FILE_SELECT_CODE = 0;
    private static ListView list;
    private static  boolean IsReached = false;
    //private static final int PICKFILE_RESULT_CODE = 8778;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_system_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out  ?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        initialSystemView.super.onBackPressed();
                    }
                }).create().show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initial_system_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(".adminFromSettings");
            startActivity(intent);

        }
        else if(id == R.id.action_backup){

            final String SAMPLE_DB_NAME = "Zaoga.db";
            AlertDialog.Builder alertWrong = new AlertDialog.Builder(initialSystemView.this);

            alertWrong.setMessage("Backup data?").setCancelable(false)
                    .setPositiveButton("Backup", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File sd = Environment.getExternalStorageDirectory();
                            File data = Environment.getDataDirectory();
                            FileChannel source=null;
                            FileChannel destination=null;
                            String currentDBPath = "/data/"+ "com.example.kudzai.EasySec" +"/databases/"+SAMPLE_DB_NAME;

                            File folder = new File(Environment.getExternalStorageDirectory() +
                                    File.separator + "EasySecretary/Backup");
                            boolean success = true;
                            if (!folder.exists()) {
                                success = folder.mkdirs();
                            }
                            Date_Operations dateop = new Date_Operations();
                            String currenttime = dateop.GetCurrentTimeAndDate();
                            String backupDBPath = "" +
                                    "EasySecretary/Backup/"+SAMPLE_DB_NAME+currenttime;
                            File currentDB = new File(data, currentDBPath);
                            File backupDB = new File(sd, backupDBPath);
                            try {
                                source = new FileInputStream(currentDB).getChannel();
                                destination = new FileOutputStream(backupDB).getChannel();
                                destination.transferFrom(source, 0, source.size());
                                source.close();
                                destination.close();
                                Toast.makeText(getApplicationContext(),"Backup Successful",
                                        Toast.LENGTH_LONG).show();
                            } catch(IOException e) {
                                e.printStackTrace();

                                Toast.makeText(getApplicationContext(),"failed",
                                        Toast.LENGTH_LONG).show();
                            }




                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });
            AlertDialog alert = alertWrong.create();
            alert.setTitle("Warning");
            alert.show();



        }
        else if(id == R.id.action_report){

            Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
            intent2.setType("file/*");
            startActivityForResult(intent2, 8778);
        }
        else{

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

                myDb = new Db_Operations(getActivity());

                View rootView = inflater.inflate(R.layout.activity_home, container, false);

                Button refresh = (Button)rootView.findViewById(R.id.btnRefresh);
                refresh.setVisibility(View.VISIBLE);
                hmeupdate = (TextView)rootView.findViewById(R.id.hmeupdate);
                list = (ListView)rootView.findViewById(R.id.list6);
                lvlist = new ArrayList<LeaveTaken>();
                progress = (ProgressBar)rootView.findViewById(R.id.progress);
                progress.setVisibility(View.GONE);
                cursor = myDb.getAllData("Leave");
                if(!cursor.moveToFirst()){
                    hmeupdate.setVisibility(View.VISIBLE);
                }

               else{
                    hmeupdate.setVisibility(View.GONE);
                    cursor.moveToFirst();
                    do{
                        lvlist.add(new LeaveTaken(cursor));
                    }
                    while(cursor.moveToNext());

                }


                    reportadapter = new homeAdapter(getActivity(),R.layout.reportlist_view,lvlist);

                    list.setAdapter(reportadapter);
                    //reportadapter.notifyDataSetChanged();



                refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        lvlist.clear();

                        cursor = myDb.getAllData("Leave");
                        if(!cursor.moveToFirst()){
                            hmeupdate.setVisibility(View.VISIBLE);

                        }

                        else{
                            hmeupdate.setVisibility(View.GONE);
                            cursor.moveToFirst();
                            do{
                                lvlist.add(new LeaveTaken(cursor));
                            }
                            while(cursor.moveToNext());

                        }


                        reportadapter = new homeAdapter(getActivity(),R.layout.reportlist_view,lvlist);

                        list.setAdapter(reportadapter);



                    }
                });






                return rootView;

            }


            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){

                myDb = new Db_Operations(getActivity());



                final View rootView = inflater.inflate(R.layout.fragment_leave, container, false);

                spnType = (Spinner)rootView.findViewById(R.id.spnleavetype);
                spndaytype = (Spinner)rootView.findViewById(R.id.spndaytype);
                fullname = (EditText)rootView.findViewById(R.id.full_name);

                startdate = (EditText)rootView.findViewById(R.id.edtxtEmail);

                disableSoftInputFromAppearing(startdate);
                enddate = (EditText)rootView.findViewById(R.id.edtxtCode);
                disableSoftInputFromAppearing(enddate);

                notes = (EditText)rootView.findViewById(R.id.edtxtNotes);


                startdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Date_Operations dateop = new Date_Operations();
                        dateop.appDate(startdate,getActivity());

                    }
                });

                disableSoftInputFromAppearing(enddate);
                enddate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Date_Operations dateop = new Date_Operations();
                        dateop.appDate(enddate,getActivity());

                    }
                });

                String []  dropdowntype = new String[]{"Select leave type","Medical leave","Vacational Leave", "Maternity Leave","Other"};
                String []  dayType = new String[]{"Select type of day","Whole Day","Half (AM)", "Half (PM)"};

                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, dropdowntype);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, dayType);
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spnType.setAdapter(adapter);
                spndaytype.setAdapter(adapter2);



                spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String leavetype = ((Spinner)rootView.findViewById(R.id.spnleavetype)).getSelectedItem().toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                        // sometimes you need nothing here
                    }
                });

                spndaytype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String Spinnergettype = ((Spinner)rootView.findViewById(R.id.spndaytype)).getSelectedItem().toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                        // sometimes you need @style/TextAppearance.AppCompatnothing here
                    }
                });


                /*Button btntest = (Button)rootView.findViewById(R.id.btntest);
                btntest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "klicked", Toast.LENGTH_SHORT).show();

                       new SendMail().execute();
                    }
                });*/
                //on button click listenner for inserting into db

                btnbSub = (Button)rootView.findViewById(R.id.btnSubmit);
                btnbSub.setOnClickListener(

                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String leavetype = spnType.getSelectedItem().toString();
                                String daytype = spndaytype.getSelectedItem().toString();

                                if (fullname.length() == 0 ||startdate.length() == 0 || enddate.length() == 0 ){

                                    Toast.makeText(getActivity(), "Fill in all the required input fields", Toast.LENGTH_SHORT).show();

                                }
                                else if(spnType.getSelectedItem().toString() == "Select leave type"){
                                    Toast.makeText(getActivity(), "Select type of leave", Toast.LENGTH_SHORT).show();
                                }
                                else if(spndaytype.getSelectedItem().toString()=="Select type of day"){
                                    Toast.makeText(getActivity(), "Select type of day", Toast.LENGTH_SHORT).show();
                                }
                                else{

                                    try{

                                        TestInternet TI = new TestInternet();


                                        if(TI.checkOnlineState(getActivity()) == true){
                                            try {
                                                new ServiceLogin().execute(leavetype, startdate.getText().toString(), enddate.getText().toString(), daytype, notes.getText().toString());

                                                boolean isInserted = myDb.insertLeave("leave", fullname.getText().toString(), leavetype , startdate.getText().toString(),
                                                        enddate.getText().toString(), daytype, notes.getText().toString());
                                                new EmailSenderAsync().execute("kidkudzy@gmail.com"
                                                        , fullname.getText().toString()+" applied for a "+leavetype+
                                                                " from "+startdate.getText().toString()+" to "+enddate.getText().toString()+"\n Regards \n Simba Education \n Email was generated by REPORTit Mobile APP","Leave Application");

                                                if (isInserted == true) {
                                                    new AlertDialog.Builder(getActivity())
                                                            .setTitle("Success")
                                                            .setMessage("Leave request submitted \n Email send to HOD \n Mr Kudzai Makufa ")
                                                          .setPositiveButton(android.R.string.yes, null).create().show();
                                                    // fullname.setText("");startdate.setText("");enddate.setText("");notes.setText("");
                                                } else {
                                                    Toast.makeText(getActivity(), "Failed to submit Request", Toast.LENGTH_SHORT).show();
                                                }





                                            }
                                            catch (Exception e){
                                                ShowMessage so = new ShowMessage();
                                                so.showMessage(getActivity(),"Error","cannot submit leave form no connection");
                                            }

                                        }
                                        else{
                                            ShowMessage so = new ShowMessage();
                                            so.showMessage(getActivity(),"Error","Not connected to Internet");
                                        }




                                    }catch(Exception e){
                                        Toast.makeText(getActivity(), "Fill in the amount input fields", Toast.LENGTH_SHORT).show();
                                    }


                                }

                                // Intent intent = new Intent(".ShowSubscriptions");

                                //startActivity(intent);



                            }



                        }
                );










                return rootView;
            }




            else{
                final View rootView = inflater.inflate(R.layout.activity_report, container, false);

                final String[] crits = new String[5];

                final List<EditText> goals = new ArrayList<EditText>();
                final List<EditText> objectives = new ArrayList<EditText>();


                final List<EditText> task = new ArrayList<EditText>();
                final List<EditText> deadline = new ArrayList<EditText>();
                final List<EditText> status = new ArrayList<EditText>();


                final EditText edtxtinitGoal = (EditText)rootView.findViewById(R.id.edtxtinitGoal);
                final EditText edtxtinitObj = (EditText)rootView.findViewById(R.id.edtxtinitObj);
                btnsubmitReport = (Button)rootView.findViewById(R.id.btnReportSubmit);
                btntask = (Button)rootView.findViewById(R.id.btntask);
                edtxtReportDate = (EditText)rootView.findViewById(R.id.edtxtReportDate);
                final EditText edtxtname = (EditText)rootView.findViewById(R.id.edtxtname);
                final EditText edtxtReportNum =  (EditText)rootView.findViewById(R.id.edtxtReportNum);
                taskdate = (EditText)rootView.findViewById(R.id.taskdate);
                final EditText taskdescription = (EditText)rootView.findViewById(R.id.taskDescription);

                taskdescription.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (taskdescription.getText().length() == 10 && !IsReached ){
                            taskdescription.append("\n");
                            IsReached = true;
                        }
                        if(taskdescription.getText().length() < 10 && IsReached) IsReached = false;

                    }
                });

                final EditText taskstatus = (EditText)rootView.findViewById(R.id.taskstatus);

                layoutgoal = (LinearLayout)rootView.findViewById(R.id.layout1);
                layoutobj = (LinearLayout)rootView.findViewById(R.id.layoutObj);
                linearTasks = (LinearLayout)rootView.findViewById(R.id.linearTasks);

                btnaddobjectives = (Button)rootView.findViewById(R.id.btnobjectives);
                btnaddgoals = (Button)rootView.findViewById(R.id.btnaddgoals);

                disableSoftInputFromAppearing(edtxtReportDate);
                disableSoftInputFromAppearing(taskdate);
                edtxtReportDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Date_Operations dateop = new Date_Operations();
                        dateop.appDate(edtxtReportDate,getActivity());

                    }
                });

                taskdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Date_Operations dateop = new Date_Operations();
                        dateop.appDate(taskdate,getActivity());

                    }
                });



                btnaddgoals.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        EditText edtxtgoals ;
                        for(int i = 0; i < 5; i++){

                            edtxtgoals = new EditText(getActivity());
                            edtxtgoals.setHint(""+i);
                            edtxtgoals.setId(i);
                            goals.add(edtxtgoals);


                            if(edtxtgoals.getParent() != null) {
                                ((ViewGroup)edtxtgoals.getParent()).removeView(edtxtgoals);
                            }
                            layoutgoal.removeView(rootView);
                            layoutgoal.addView(edtxtgoals);
                        }
                    }
                });

                btnaddobjectives.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        objectives.add(edtxtinitObj);
                        for(int x = 0;x<5 ; x++){
                            edtxtobj = new EditText(getActivity());
                            edtxtobj.setHint(""+x);
                            edtxtobj.setId(x);
                            objectives.add(edtxtobj);

                            if(edtxtobj.getParent() != null) {
                                ((ViewGroup)edtxtobj.getParent()).removeView(edtxtobj);
                            }
                            layoutobj.removeView(rootView);
                            layoutobj.addView(edtxtobj);


                        }
                    }
                });

                btnsubmitReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(deadline.size() == 0 && task.size() == 0  && status.size() == 0){
                            if(taskdate.getText().toString() == "" ||taskdescription.getText().toString() == "" || taskstatus.getText().toString() == ""){
                                Toast.makeText(getActivity(), "enter all task details", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                deadline.add(taskdate); task.add(taskdescription);status.add(taskstatus);
                            }
                        }
                        else{

                        }

                        if(goals.size() == 0){
                            if(edtxtinitGoal.getText().toString() == ""){
                                Toast.makeText(getActivity(), "Enter goals", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                goals.add(edtxtinitGoal);
                            }
                        }

                        if(objectives.size() == 0){
                            if(edtxtinitObj.getText().toString() == ""){
                                Toast.makeText(getActivity(), "Enter objetives", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                objectives.add(edtxtinitObj);
                            }
                        }


                        //Toast.makeText(getActivity(), goals.get(2).getText().toString(), Toast.LENGTH_SHORT).show();
                        Pdf_Op_Method pdfop = new Pdf_Op_Method();

                            //Generate Pdf
                            pdfop.ReportPdf(getActivity(),edtxtReportDate.getText().toString()
                            ,edtxtname.getText().toString(),edtxtReportNum.getText().toString()
                            ,goals,objectives,task,deadline,status);
                    }
                });

                btntask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int x = 0;x<1 ; x++){

                            taskname = new TextView(getActivity());
                            taskname.setText("Task");
                            taskname.setTextColor(Color.parseColor( "#34ACD6"));
                            taskname.setTypeface(null , Typeface.BOLD);
                            taskname.setGravity(Gravity.CENTER);
                            taskname.setTextSize(15);

                            edtxtdeadline = new EditText(getActivity());
                            edtxtdeadline.setHint("Deadline");
                            edtxtdeadline.setId(x);
                            deadline.add(taskdate);
                            deadline.add(edtxtdeadline);
                            disableSoftInputFromAppearing(edtxtdeadline);
                            edtxtdeadline.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {



                                    Date_Operations dateop = new Date_Operations();
                                    dateop.appDate(edtxtdeadline,getActivity());

                                }
                            });

                            edtxtdescrip = new EditText(getActivity());
                            edtxtdescrip.setHint("enter task description");
                            edtxtdescrip.setId(x);
                            task.add(taskdescription);
                            task.add(edtxtdescrip);

                            edtxtstatus = new EditText(getActivity());
                            edtxtstatus.setHint("Status");
                            edtxtstatus.setId(x);
                            status.add(taskstatus);
                            status.add(edtxtstatus);



                            if(edtxtdeadline.getParent() != null) {
                                ((ViewGroup)edtxtdeadline.getParent()).removeView(edtxtdeadline); // <- fix
                            }
                            if(edtxtdescrip.getParent() != null) {
                                ((ViewGroup)edtxtdescrip.getParent()).removeView(edtxtdescrip); // <- fix
                            }
                            if(edtxtstatus.getParent() != null) {
                                ((ViewGroup)edtxtstatus.getParent()).removeView(edtxtstatus); // <- fix
                            }

                            linearTasks.removeView(rootView);
                            linearTasks.addView(taskname);
                            linearTasks.addView(edtxtdeadline);
                            linearTasks.addView(edtxtdescrip);
                            linearTasks.addView(edtxtstatus);


                        }
                    }
                });

                /*t = (Button)rootView.findViewById(R.id.btnpick);

                pickreport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                        intent.addCategory(Intent.CATEGORY_OPENABLE);

                        intent.setType("**");

                        startActivityForResult(Intent.createChooser(intent, "Open Doc"), FILE_SELECT_CODE);
                    }
                });*/




                return rootView;
            }

        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }



            // you need to check how you can make add data static and getActivity on context


}
