package com.davidroach.assignment3paint;

/**
 * Created by TBCJr on 3/15/17.
 */

public class Rotate {

    /*
    *
    *
public class CustomView extends View {

  private int stateToSave;

  ...

  @Override
  public Parcelable onSaveInstanceState() {
    //begin boilerplate code that allows parent classes to save state
    Parcelable superState = super.onSaveInstanceState();

    SavedState ss = new SavedState(superState);
    //end

    ss.stateToSave = this.stateToSave;

    return ss;
  }

  @Override
  public void onRestoreInstanceState(Parcelable state) {
    //begin boilerplate code so parent classes can restore state
    if(!(state instanceof SavedState)) {
      super.onRestoreInstanceState(state);
      return;
    }

    SavedState ss = (SavedState)state;
    super.onRestoreInstanceState(ss.getSuperState());
    //end

    this.stateToSave = ss.stateToSave;
  }

  static class SavedState extends BaseSavedState {
    int stateToSave;

    SavedState(Parcelable superState) {
      super(superState);
    }

    private SavedState(Parcel in) {
      super(in);
      this.stateToSave = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
      super.writeToParcel(out, flags);
      out.writeInt(this.stateToSave);
    }

    //required field that makes Parcelables from a Parcel
    public static final Parcelable.Creator<SavedState> CREATOR =
        new Parcelable.Creator<SavedState>() {
          public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
          }
          public SavedState[] newArray(int size) {
            return new SavedState[size];
          }
    };
  }
}
    *
    *
    * */

}
