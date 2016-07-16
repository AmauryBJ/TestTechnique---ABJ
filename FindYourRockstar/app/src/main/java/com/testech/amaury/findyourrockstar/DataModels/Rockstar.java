package com.testech.amaury.findyourrockstar.DataModels;

/**
 * Created by Amou on 16/07/16.
 */
public class Rockstar {

    //region Properties

    public String firstname;
    public String lastname;
    public String status;
    public String hisface;

    //endregion

    //region Constructor

    public Rockstar(String _firstname, String _lastname, String _status, String _hisface)
    {
        firstname = _firstname;
        lastname = _lastname;
        status = _status;
        hisface = _hisface;
    }

    //endregion

    //region Override methods

    public String toString() {
        return firstname + " - " + lastname ;
    }

    //endregion


}
