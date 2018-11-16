package deptinfo.ubfc.sampquiz.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import deptinfo.ubfc.sampquiz.Question;
import deptinfo.ubfc.sampquiz.R;
import deptinfo.ubfc.sampquiz.database.DataBase;
import deptinfo.ubfc.sampquiz.database.DataBaseHelper;

import static java.lang.Math.max;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViewById(R.id.quitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        findViewById(R.id.dlButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(StartActivity.this)
                        .setTitle("Download Quizzes")
                        .setMessage("Do you really want to download quizzes from 'https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml' ? Downloaded quizzes will override existing ones.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                new Page().execute();
                                Toast.makeText(StartActivity.this,"Quizzes downloaded ! ",Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                //TODO Download from specified server
            }
        });

        findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Edit quiz from database
            }
        });

        findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ChooseQuizPlayActivity.class);
                startActivity(intent);
            }
        });
    }

    private void downloadXML() {
        BufferedReader reader = null;
        HttpURLConnection urlC = null;

        try {
            URL url = new URL("https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml");

            urlC = (HttpURLConnection) url.openConnection();
            int responseCode = urlC.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = urlC.getInputStream();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder dab = dbf.newDocumentBuilder();
                Document doc = dab.parse (inputStream);
                doc.getDocumentElement().normalize ();

                DataBaseHelper dataBaseHelper = new DataBaseHelper(StartActivity.this);
                SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

                Node quizzesNode = doc.getElementsByTagName("Quizzs").item(0);
                NodeList quizzes = ((Element) quizzesNode).getElementsByTagName("Quizz");
                for (int i = 0; i<quizzes.getLength();i++){
                    Node currentQuiz = quizzes.item(i);
                    String quizName = currentQuiz.getAttributes().item(0).getNodeValue();
                    NodeList questions = ((Element) currentQuiz).getElementsByTagName("Question");

                    String queryExist = "DELETE FROM "+DataBase.Quiz.TABLE_NAME+" WHERE "+DataBase.Quiz.COLUMN_NAME_TITLE+" = '"+quizName+"';";
                    db.execSQL(queryExist);

                    ContentValues values = new ContentValues();
                    values.put(DataBase.Quiz.COLUMN_NAME_TITLE,quizName);
                    values.put(DataBase.Quiz.COLUMN_NAME_FIRST_NAME,"WIN");
                    values.put(DataBase.Quiz.COLUMN_NAME_FIRST_SCORE,questions.getLength()*0.8);
                    values.put(DataBase.Quiz.COLUMN_NAME_SECOND_NAME,"MED");
                    values.put(DataBase.Quiz.COLUMN_NAME_SECOND_SCORE,questions.getLength()*0.6);
                    values.put(DataBase.Quiz.COLUMN_NAME_THIRD_NAME,"LOS");
                    values.put(DataBase.Quiz.COLUMN_NAME_THIRD_SCORE,questions.getLength()*0.4);
                    values.put(DataBase.Quiz.COLUMN_NAME_IMAGE,"res::inter");
                    long quizId = db.insert(DataBase.Quiz.TABLE_NAME, null, values);

                    for (int j = 0; j<questions.getLength();j++){
                        Node currentQuestion = questions.item(j);
                        String text = String.valueOf(currentQuestion.getChildNodes().item(0).getNodeValue()).replaceAll("\t","");
                        String reponse = ((Element) currentQuestion).getElementsByTagName("Reponse").item(0).getAttributes().item(0).getNodeValue();
                        long questionId = dataBaseHelper.addQuestion(db, text, Integer.parseInt(reponse)-1, quizId);

                        NodeList answers = ((Element) ((Element) currentQuestion).getElementsByTagName("Propositions").item(0)).getElementsByTagName("Proposition");
                        for (int k = 0; k<answers.getLength(); k++){
                            Node currentAnswer = answers.item(k);
                            String textA = String.valueOf(currentAnswer.getChildNodes().item(0).getNodeValue()).replaceAll("\t","");
                            textA = textA.substring(1,textA.length()-1);
                            ContentValues valuesA = new ContentValues();
                            valuesA.put(DataBase.Answer.COLUMN_NAME_TEXT,textA);
                            valuesA.put(DataBase.Answer.COLUMN_NAME_QUESTION,questionId);
                            db.insert(DataBase.Answer.TABLE_NAME, null, valuesA);
                        }
                    }
                }


                db.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            if( reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlC != null) urlC.disconnect();
        }

    }

    private class Page extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            downloadXML();
            return null;
        }
    }
}
