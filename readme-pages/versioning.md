## DETAILS ABOUT VERSIONING

More specifically, this project uses the following standardization: **_Major.Minor.Patch-ReleaseStatus+Build_**, where:
* The standard values of **_Major_**, **_Minor_**, and **_Patch_** for the _VerExpr_ are as follows:
  + Positive integer decimal numbers, without zero remaining on the left;
  + **_Major_** version represents wider changes in the project, which affects the main structure of the project, or its main objectives, or the last user API released;
  + **_Minor_** version represents smaller changes in the project, which don't affect above itens, but affect the amount of the application fuatrures with a new one or more, or remove an existing feature previouslly released;
  + **_Patch_** version represents specific changes which goals to fix or improve some feature, or undesired behavior in the application.  
* The standard flags of **_ReleaseStatus_** for the _VerExpr_ are as follows:
  + **_dev_**: in early development, usage not encouraged;
  + **_alpha_**: in development, first test phase, it's encouraged usage for **test only** by people involved with software development, at self-own risk;
  + **_beta_**: in pre-release version, general public usage is acceptable, however, **only for test**, usage is a choice at self-own risk;
  + **_release_**: release version; relatively stable in proportion to the effectiveness of the tests; bugs are possible to appear, so it would come back to a _hotfix-branch_ if needed.
* The standard values of **_Build_** for the _VerExpr_ are as follows:
  + A 12-digit numeric sequence, positive integer decimal digits, formatted somewhat to ISO 8601 DateTime YYYYMMDDhhmm;
  + The initial 4 digits (YYYY) represent the year;
  + The next 2 digits (MM) represent the month;
  + The next 2 digits (DD) represent the day;
  + The next 2 digits (hh) represent the hour;
  + The following 2 digits (mm) represent the minutes;
  + All of aboce referecing to the moment when the developer builds/exports the .JAR file (* 1).  
>  
> (* 1) The numerical sequence _Build_ necessarily/obligatorily refers to Greenwich Mean Time (GMT), also known as  Universal Time Coordinate (UTC), or "Z time" or "Zulu time".  
>  
> Example of **_Build_**: "202612311859".
>  
> Full example of **_Versioning-Expression_**: `1.2.3-release+202612311859`, meaning `1`._ ._ version fully implemented according to the project and its backlog; added by _ .`2`._ additional features to the main version, according to the project backlog and its issues priorities in the **_SCRUM life cycle_**; added by _ ._ .`3` patches fixed in this mentioned lastest version following the **_GITFLOW life cycle_**, that means, it is a released version after passed by the tests in **_alpha_** and **_beta_** pre-releases; and finally, it was specifically build at the year 2026 month 12 (December) day 31 at 18:59h at UTC/GMT/Z-time/Zulu-time (18hours and 59minutes equals 6p.m and 59minutes in some idioms).  

&nbsp;  
&nbsp;  
&nbsp;  
