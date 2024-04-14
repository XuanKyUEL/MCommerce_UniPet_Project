package com.unipet7.mcommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.models.Questions;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {


    Context context;
    List<Questions> questionsList;

    public QuestionAdapter(Context context, List<Questions> questionsList) {
        this.context = context;
        this.questionsList = questionsList;
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_questions_support, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {
        Questions questions = questionsList.get(position);
        holder.txtQuestion.setText(questions.getQuestion());
        holder.txtAnswer.setText(questions.getAnswer());

        holder.txtAnswer.setVisibility(View.GONE);
        holder.rl_descrip_line.setVisibility(View.GONE);
        holder.rl_title_line.setVisibility(View.VISIBLE);
        holder.txtQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.txtAnswer.getVisibility() == View.GONE) {
                    // Nếu mô tả đang ẩn, hiển thị mô tả
                    holder.txtAnswer.setVisibility(View.VISIBLE);
                    holder.rl_descrip_line.setVisibility(View.VISIBLE);
                    holder.rl_title_line.setVisibility(View.GONE);
                } else {
                    // Nếu mô tả đang hiển thị, ẩn mô tả
                    holder.txtAnswer.setVisibility(View.GONE);
                    holder.rl_descrip_line.setVisibility(View.GONE);
                    holder.rl_title_line.setVisibility(View.VISIBLE);
                }
            }
        });
//

    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestion, txtAnswer;
        RelativeLayout rl_title_line;
        RelativeLayout rl_descrip_line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.question);
            txtAnswer = itemView.findViewById(R.id.answer);
            rl_title_line = itemView.findViewById(R.id.rlt_title);
            rl_descrip_line = itemView.findViewById(R.id.rlt_descript);


        }
    }

}
