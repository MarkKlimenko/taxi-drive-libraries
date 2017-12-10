## Tda district mapper library
### Usage
<pre>
List mapper = [
            [streetId: 'svt', building: '1-24В', districtId: 'cnt'],
            [streetId: 'svt', building: '24Г-24/3', districtId: 'dal'],
            [streetId: 'svt', building: '24/4-106', districtId: 'lug'],
            [streetId: 'svt', building: '107-107/2', districtId: 'spor'],
            [streetId: 'svt', building: '107/3-107/9', districtId: 'rog'],
            [streetId: 'svt', building: '108-112/6', districtId: 'tih'],
    ]
    
Map address = [streetId: 'svt', building: '25']

new DistrictMapperService().getDistrict(mapper, address)
</pre>

DistrictMapperService returns district id 'lug' for target address

### Publish library
<pre>
gradlew bintrayUpload -PbintrayUser= -PbintrayKey=
</pre>