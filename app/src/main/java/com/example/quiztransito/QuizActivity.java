package com.example.quiztransito;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quiztransito.R;

public class QuizActivity extends AppCompatActivity {
    private int currentQuestionIndex = 0;
    private int score = 0;

    private int[] questionImages = {
            R.drawable.placa_1, // Junções sucessivas contrárias primeira à direita
            R.drawable.placa_2, // Estreitamento de pista à direita
            R.drawable.placa_3, // Ponte Estreita
            R.drawable.placa_4, // Proibido estacionar
            R.drawable.placa_5  // Dê a preferência
    };

    private String[][] optionsText = {
            {"Sinal de Pare", "Junções sucessivas contrárias primeira à direita", "Sinal de Atenção", "Sinal de Despacho de Carga"}, // Opções para a questão 1
            {" Estreitamento de pista à direita", "Sinal de Entrada Proibida", "Sinal de Ceda a Passagem", "Sinal de Estacionamento"}, // Opções para a questão 2
            {"Sinal de Proibido Virar à Direita", "Sinal de Atenção ao Pedestre", "Sinal de Proibido Ultrapassar", "Ponte Estreita"}, // Opções para a questão 3
            {"Sinal de Proibido Fumar", "Sinal de Semáforo", "Proibido estacionar", "Sinal de Área Escolar"}, // Opções para a questão 4
            {"Dê a preferência", "Sinal de Proibido Dobrar", "Sinal de Obrigatório Parar", "Sinal de Velocidade Mínima"} // Opções para a questão 5
    };

    private int[] correctAnswers = {R.id.option2, R.id.option1, R.id.option4, R.id.option3, R.id.option1}; // IDs das respostas corretas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final ImageView trafficSignImage = findViewById(R.id.trafficSignImage);
        final RadioGroup answerGroup = findViewById(R.id.answerGroup);
        final Button submitButton = findViewById(R.id.submitButton);

        loadQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOptionId = answerGroup.getCheckedRadioButtonId();
                if (selectedOptionId == correctAnswers[currentQuestionIndex]) {
                    score++;
                }

                currentQuestionIndex++;
                if (currentQuestionIndex < questionImages.length) {
                    loadQuestion();
                } else {
                    Intent intent = new Intent(QuizActivity.this, RankingActivity.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void loadQuestion() {
        ImageView trafficSignImage = findViewById(R.id.trafficSignImage);
        trafficSignImage.setImageResource(questionImages[currentQuestionIndex]);

        RadioGroup answerGroup = findViewById(R.id.answerGroup);
        // Resetar as opções
        for (int i = 0; i < answerGroup.getChildCount(); i++) {
            View view = answerGroup.getChildAt(i);
            if (view instanceof RadioButton) {
                ((RadioButton) view).setChecked(false);
            }
        }

        // Configurar as opções para a pergunta atual
        String[] currentOptions = optionsText[currentQuestionIndex];
        for (int i = 0; i < currentOptions.length; i++) {
            RadioButton option = findViewById(getResources().getIdentifier("option" + (i + 1), "id", getPackageName()));
            option.setText(currentOptions[i]);
        }
    }
}
