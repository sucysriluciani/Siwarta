package go.id.payakumbuh.siwarta.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import go.id.payakumbuh.siwarta.model.Model;

/**
 * Created by Anggrayudi on 23/08/2016.
 */
abstract class AdapterManagerHelper<M extends Model, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private static final String TAG = "AdapterManagerHelper";

    final RecyclerView recyclerView;
    final List<M> mValues;
    final SparseIntArray valsById;
    final SparseBooleanArray selectedItems = new SparseBooleanArray();

    AdapterManagerHelper(RecyclerView recyclerView, List<M> mValues){
        this.recyclerView = recyclerView;
        this.mValues = mValues;
        valsById = new SparseIntArray(mValues.size());
        reorder();
    }

    @Override
    public int getItemCount() {
        return mValues == null ? 0 : mValues.size();
    }

    public void addAll(List<M> models) {
        if (models.isEmpty()) return;
        mValues.addAll(models);
        valsById.clear();
        reorder();
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        valsById.delete(getValueAt(position).id);
        mValues.remove(position);
        reorder();
        notifyItemRemoved(position);
    }

    public void removeById(int id) {
        int pos = valsById.get(id, -1);
        valsById.delete(id);
        mValues.remove(pos);
        reorder();
        notifyItemRemoved(pos);
    }

    public void removeAllById(Collection<Integer> ids) {
        if (ids.isEmpty()) return;
        List<Integer> positions = new ArrayList<>(ids.size());
        for (int id : ids)
            positions.add(valsById.get(id));

        Collections.sort(positions, Collections.reverseOrder());
        for (int pos : positions) {
            mValues.remove(pos);
        }
        valsById.clear();
        reorder();
        notifyDataSetChanged();
    }

    public M getValueAt(int position) {
        return mValues.get(position);
    }

    public M getValueById(int id) {
        id = getPositionById(id);
        return id == -1 ? null : getValueAt(id);
    }

    public List<M> getValues() {
        return mValues;
    }

    public int getPositionById(int id) {
        return valsById.get(id, -1);
    }

    public boolean removeSelected() {
        List<Integer> positions = getSelectedItems();
        Collections.reverse(positions);
        for (int pos : positions) {
            mValues.remove(pos);
        }
        valsById.clear();
        reorder();
        notifyDataSetChanged();
        return mValues.isEmpty();
    }

    public void toggleSelection(int position) {
        if (selectedItems.get(position, false))
            selectedItems.delete(position);
        else
            selectedItems.put(position, true);

        notifyItemChanged(position);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<M> getSelectedModels() {
        List<M> models = new ArrayList<>(getSelectedItemCount());
        for (int pos : getSelectedItems())
            models.add(getValueAt(pos));
        return models;
    }

    public ArrayList<Integer> getSelectedItems() {
        ArrayList<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        Collections.sort(items);
        return items;
    }

    public ArrayList<Integer> getSelectedItemIds() {
        ArrayList<Integer> ids = new ArrayList<>(getSelectedItemCount());
        for (int pos : getSelectedItems())
            ids.add(getValueAt(pos).id);
        return ids;
    }

    public void selectAll() {
        for (int i = 0; i < mValues.size(); i++)
            selectedItems.put(i, true);

        notifyItemRangeChanged(0, selectedItems.size());
    }

    public void clearList() {
        mValues.clear();
        valsById.clear();
        selectedItems.clear();
    }

    /**
     * Reorder the position
     */
    final void reorder(){
        for (int i = 0; i < mValues.size(); i++) {
            valsById.put(mValues.get(i).id, i);
        }
    }

    final void refresh(){
        valsById.clear();
        reorder();
        notifyDataSetChanged();
    }
}
