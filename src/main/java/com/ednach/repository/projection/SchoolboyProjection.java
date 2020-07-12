package com.ednach.repository.projection;
/**
* declare a projection interface for the schoolboy class
*/
public interface SchoolboyProjection {

    Long getSchoolboyId();

    Long getUserId();

    String getUserFirstName();

    String getUserLastName();

    Long getClassroomId();

    String getClassName();
}
