<sql id="ExampleWhereClause">
  <isNotNull property="oredCriteria">
    WHERE
    <iterate  property="oredCriteria" conjunction="OR" removeFirstPrepend="true">
      <isEqual property="oredCriteria[].valid"  prepend="AND" compareValue="true" removeFirstPrepend="true">(
        <iterate  property="oredCriteria[].criteria" >
          <isEqual property="oredCriteria[].criteria[].noValue" compareValue="true" removeFirstPrepend="true" prepend="AND">
            $oredCriteria[].criteria[].condition$
          </isEqual>
          <isEqual property="oredCriteria[].criteria[].singleValue" compareValue="true" removeFirstPrepend="true" prepend="AND">
            $oredCriteria[].criteria[].condition$ #oredCriteria[].criteria[].value#
          </isEqual>
          <isEqual property="oredCriteria[].criteria[].betweenValue" compareValue="true" removeFirstPrepend="true" prepend="AND">
            $oredCriteria[].criteria[].condition$ #oredCriteria[].criteria[].value# AND #oredCriteria[].criteria[].secondValue#
          </isEqual>
          <isEqual property="oredCriteria[].criteria[].listValue" compareValue="true" removeFirstPrepend="true" prepend="AND">
            $oredCriteria[].criteria[].condition$
            <iterate  property="oredCriteria[].criteria[].value" conjunction="," >
              (#oredCriteria[].criteria[].value[]#)
            </iterate >
          </isEqual>
        </iterate >
      )</isEqual>
    </iterate >
  </isNotNull>
</sql>