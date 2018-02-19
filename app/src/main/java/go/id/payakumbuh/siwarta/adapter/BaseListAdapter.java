package go.id.payakumbuh.siwarta.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.id.payakumbuh.siwarta.R;
import go.id.payakumbuh.siwarta.model.Model;

/**
 * Created by anggrayudi on 19/02/18.
 */

public class BaseListAdapter extends AdapterManagerHelper<Model, BaseListAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(android.R.id.title) TextView title;
        @BindView(android.R.id.summary) TextView summary;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = (int) v.getTag();
        }
    }

    public BaseListAdapter(RecyclerView recyclerView, List<Model> mValues) {
        super(recyclerView, mValues);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.model_base, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setActivated(selectedItems.get(position, false));
        Model model = getValueAt(position);
        holder.title.setText(model.title);
        holder.summary.setText(model.summary);
        holder.itemView.setTag(model.id);
    }
}
