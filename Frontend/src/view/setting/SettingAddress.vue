<template>
  <div class="view-container">
    <setting-side></setting-side>
    <div class="view-main">
      <div class="setting-container" @click="watchMouse">
        <h2 class="h2-heading">Address</h2>
        <div>
          <div id="region-block">
            <h3 class="h3-heading">Region</h3>
            <input
              id="input-region"
              class="input-block input-control"
              type="text"
              :value="regionName"
              @focus="changeShowRegion"
            >
            <ul id="province" v-show="showRegion">
              <li
                class="region-list"
                v-for="provin in provinFilter"
                :key="provin"
                @click="modifyProv(provin)"
              >{{provin}}</li>
            </ul>
            <ul id="city" v-show="showRegion">
              <li
                class="region-list"
                v-for="city in cityFilter"
                :key="city"
                @click="modifyCity(city)"
              >{{city}}</li>
            </ul>
            <ul id="district" v-show="showRegion">
              <li
                class="region-list"
                v-for="dist in distFilter"
                :key="dist"
                @click="modifyDist(dist)"
              >{{dist}}</li>
            </ul>
          </div>
          <h3 class="h3-heading">Detail address</h3>
          <input id="input-detail" class="input-block input-control" type="text" v-model="addr">
        </div>
        <button class="btn btn-block btn-submit">Update address</button>
      </div>
    </div>
  </div>
</template>
<script>
import SettingSide from '../../components/setting/SettingSide'
export default {
  name: 'SettingAddress',
  components: {
    SettingSide
  },
  data () {
    return {
      showRegion: false,
      addr: '',
      prov: '',
      city: '',
      district: '',
      region: {
        prov: '',
        city: '',
        dist: ''
      },
      regionList: [
        {
          province: 'Shanghai',
          children: [
            {
              city: '黄浦区',
              children: []
            },
            {
              city: '徐汇区',
              children: []
            },
            {
              city: '长宁区',
              children: []
            },
            {
              city: '静安区',
              children: []
            },
            {
              city: '普陀区',
              children: []
            },
            {
              city: '虹口区',
              children: []
            }
          ]
        },
        {
          province: 'Jiangsu',
          children: [
            {
              city: 'Nanjing',
              children: [
                {
                  district: '玄武区'
                },
                {
                  district: '秦淮区'
                },
                {
                  district: '鼓楼区'
                },
                {
                  district: '建邺区'
                },
                {
                  district: '雨花台区'
                },
                {
                  district: '江宁区'
                }
              ]
            },
            {
              city: 'Zhenjiang',
              children: [
                {
                  district: '京口区'
                },
                {
                  district: '润州区'
                },
                {
                  district: '丹徒区'
                },
                {
                  district: '丹阳市'
                },
                {
                  district: '扬中市'
                },
                {
                  district: '丹徒新区'
                }
              ]
            }
          ]
        }
      ]
    }
  },
  computed: {
    provinFilter () {
      var provin = []
      var list = this.regionList
      list.forEach(ele => {
        provin.push(ele.province)
      })
      return provin
    },
    regionName () {
      var name = ''
      if (this.region.prov !== '') name += this.region.prov
      if (this.region.city !== '') {
        name += '-'
        name += this.region.city
        if (this.region.dist !== '') {
          name += '-'
          name += this.region.dist
        }
      }
      return name
    },
    cityFilter () {
      var city = []
      this.regionList.forEach(ele => {
        if (ele.province === this.prov) {
          ele.children.forEach(e => {
            city.push(e.city)
          })
        }
      })
      return city
    },
    distFilter () {
      var dist = []
      this.regionList.forEach(ele => {
        if (ele.province === this.prov) {
          ele.children.forEach(e => {
            if (e.city === this.city) {
              e.children.forEach(distele => {
                dist.push(distele.district)
              })
            }
          })
        }
      })
      return dist
    }
  },
  mounted: function () {
    this.fetchAddr()
    this.fetchRegion()
  },
  methods: {
    watchMouse () {
      var e = window.event
      var id = e.target.id
      var claName = e.srcElement.className
      if (
        id !== 'province' &&
        id !== 'city' &&
        id !== 'district' &&
        id !== 'input-region' &&
        claName !== 'region-list'
      ) {
        this.showRegion = false
      } else this.showRegion = true
    },
    changeShowRegion () {
      this.showRegion = true
    },
    modifyProv (province) {
      this.prov = province
    },
    modifyCity (city) {
      this.city = city
    },
    modifyDist (dist) {
      this.district = dist
    },
    fetchAddr () {
      this.addr = 'Dongchuan Road 800 SJTU'
    },
    fetchRegion () {
      this.region.prov = 'Shanghai'
      this.region.city = 'Minhang'
    }
  },
  watch: {
    prov: function () {
      this.region.prov = this.prov
    },
    city: function () {
      this.region.city = this.city
    },
    district: function () {
      this.region.dist = this.district
    }
  }
}
</script>
<style scoped>
button {
  width: 38%;
  max-width: 200px;
  margin: 20px 0;
}
#input-region {
  width: 30%;
  max-width: 400px;
  margin-bottom: 40px;
}
#input-detail {
  width: 60%;
  max-width: 500px;
}
#region-block {
  position: relative;
  clear: both;
}
#province,
#city,
#district {
  position: absolute;
  top: 64px;
  width: 32.5%;
  background: rgba(255, 255, 255, 0.7);
}
#city {
  left: 32.5%;
}
#district {
  left: 65%;
}
.region-list {
  height: 18px;
  font-size: 16px;
  line-height: 18px;
}
.region-list:hover {
  background: rgb(233, 233, 233);
  cursor: pointer;
}
</style>
