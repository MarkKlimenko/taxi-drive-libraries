package systems.vostok.tda.component

class TestComponent {
    static List mapperSimple = [
            [streetId: 'Svetlanskaya' , building:'1-24' , districtId: 'Center' ],
            [streetId: 'Svetlanskaya' , building:'25-50' , districtId: 'Dalzavod' ],
            [streetId: 'Svetlanskaya' , building:'51-106' , districtId: 'Lugovaya' ]
    ]

    static List mapperLiteral = [
            [streetId: 'Svetlanskaya' , building:'1-24В' , districtId: 'Center' ],
            [streetId: 'Svetlanskaya' , building:'24В-24З' , districtId: 'Dalzavod' ],
            [streetId: 'Svetlanskaya' , building:'24З-106' , districtId: 'Lugovaya' ]
    ]

    static List mapperDash = [
            [streetId: 'Svetlanskaya' , building:'1-24В' , districtId: 'Center' ],
            [streetId: 'Svetlanskaya' , building:'24В-24/3' , districtId: 'Dalzavod' ],
            [streetId: 'Svetlanskaya' , building:'24/4-106' , districtId: 'Lugovaya' ]
    ]


    static void main(String[] args) {
        Map initialSimpleData = [streetId: 'Svetlanskaya', building: '25']
        testMapperSimple(initialSimpleData)

        Map initialLiteralData = [street: 'Svetlanskaya', building: '3A']
        //testMapperLiteral(initialLiteralData)

        Map initialDashData = [street: 'Svetlanskaya', building: '24/1']
        //testMapperDash(initialDashData)
    }

// SIMPLE ----------------------
    static void testMapperSimple(Map initialSimpleData) {
        mapperSimple.findAll {
            it.streetId == initialSimpleData.street
        }.each {
            if (checkSimpleAccordance(initialSimpleData, it as Map)) {
                println it.districtId
            }
        }
    }

    static Boolean checkSimpleAccordance(Map initialSimpleData, Map mapperValue) {
        ( (initialSimpleData.building as Integer) <= (mapperValue.building.split('-')[1] as Integer)) &&
                ( (initialSimpleData.building as Integer)  >= (mapperValue.building.split('-')[0] as Integer) )
    }

// END SIMPLE ----------------------


    static void testMapperLiteral(Map initialLiteralData) {
        println('test')
    }

    static void testMapperDash(Map initialDashData) {
        println('test')
    }


}
