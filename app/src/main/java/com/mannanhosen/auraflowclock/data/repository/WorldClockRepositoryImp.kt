package com.mannanhosen.auraflowclock.data.repository
import com.mannanhosen.auraflowclock.data.data_source.TimeZoneDao
import com.mannanhosen.auraflowclock.data.model.entity.TimeZone
import com.mannanhosen.auraflowclock.domain.repository.TimeZoneRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
 class TimeZoneRepositoryImp @Inject constructor(
    private val timeZoneDao: TimeZoneDao
) : TimeZoneRepository {

    override fun getAllTimeZones(): Flow<List<TimeZone>> =
        timeZoneDao.getAllTimeZones()

    override suspend fun getSelectedTimeZones(): Flow<List<TimeZone>> =
        timeZoneDao.getSelectTimeZones()

   override suspend fun getTimeZoneById(uid: Int): TimeZone? =
        timeZoneDao.getTimeZoneById(uid)

    override suspend fun insertTimeZone(timeZone: TimeZone) =
        timeZoneDao.insertTimeZone(timeZone)

    suspend fun insertTimeZones(timeZones: List<TimeZone>) =
        timeZoneDao.insertTimeZones(timeZones)

    override suspend fun updateTimeZone(timeZone: TimeZone) =
        timeZoneDao.updateTimeZone(timeZone)

    suspend fun deleteTimeZone(timeZone: TimeZone) =
        timeZoneDao.deleteTimeZone(timeZone)

    override suspend fun deleteAllTimeZones() =
        timeZoneDao.deleteAllTimeZones()

    override suspend fun updateTimeZoneSelection(uid: String, isSelected: Boolean) =
        timeZoneDao.updateTimeZoneSelection(uid, isSelected)

    override suspend fun getTimeZoneCount(): Int =
        timeZoneDao.getTimeZoneCount()

    override suspend fun initializeDefaultTimeZones() {
        val count = getTimeZoneCount()
        if (count == 0) {
            val defaultTimeZones = listOf(
                // North America
                TimeZone("america_new_york", "America/New_York", "New York"),
                TimeZone("america_los_angeles", "America/Los_Angeles", "Los Angeles"),
                TimeZone("america_chicago", "America/Chicago", "Chicago"),
                TimeZone("america_toronto", "America/Toronto", "Toronto"),
                TimeZone("america_vancouver", "America/Vancouver", "Vancouver"),
                TimeZone("america_denver", "America/Denver", "Denver"),
                TimeZone("america_phoenix", "America/Phoenix", "Phoenix"),
                TimeZone("america_mexico_city", "America/Mexico_City", "Mexico City"),
                TimeZone("america_guatemala", "America/Guatemala", "Guatemala City"),
                TimeZone("america_panama", "America/Panama", "Panama City"),
                TimeZone("america_houston", "America/Chicago", "Houston"),
                TimeZone("america_montreal", "America/Toronto", "Montreal"),
                TimeZone("america_ottawa", "America/Toronto", "Ottawa"),
                TimeZone("america_edmonton", "America/Edmonton", "Edmonton"),
                TimeZone("america_winnipeg", "America/Winnipeg", "Winnipeg"),
                TimeZone("america_halifax", "America/Halifax", "Halifax"),
                TimeZone("america_st_johns", "America/St_Johns", "St. John's"),
                TimeZone("america_cancun", "America/Cancun", "Cancún"),
                TimeZone("america_tijuana", "America/Tijuana", "Tijuana"),
                TimeZone("america_santo_domingo", "America/Santo_Domingo", "Santo Domingo"),
                TimeZone("america_havana", "America/Havana", "Havana"),
                TimeZone("america_san_juan", "America/Puerto_Rico", "San Juan"),
                TimeZone("america_nassau", "America/Nassau", "Nassau"),
                TimeZone("america_kingston", "America/Jamaica", "Kingston"),

                // South America
                TimeZone("america_sao_paulo", "America/Sao_Paulo", "São Paulo"),
                TimeZone("america_buenos_aires", "America/Argentina/Buenos_Aires", "Buenos Aires"),
                TimeZone("america_lima", "America/Lima", "Lima"),
                TimeZone("america_bogota", "America/Bogota", "Bogotá"),
                TimeZone("america_caracas", "America/Caracas", "Caracas"),
                TimeZone("america_santiago", "America/Santiago", "Santiago"),
                TimeZone("america_montevideo", "America/Montevideo", "Montevideo"),
                TimeZone("america_asuncion", "America/Asuncion", "Asunción"),
                TimeZone("america_quito", "America/Guayaquil", "Quito"),
                TimeZone("america_la_paz", "America/La_Paz", "La Paz"),
                TimeZone("america_rio_de_janeiro", "America/Sao_Paulo", "Rio de Janeiro"),
                TimeZone("america_salvador", "America/Bahia", "Salvador"),
                TimeZone("america_recife", "America/Recife", "Recife"),
                TimeZone("america_fortaleza", "America/Fortaleza", "Fortaleza"),
                TimeZone("america_medellin", "America/Bogota", "Medellín"),
                TimeZone("america_cali", "America/Bogota", "Cali"),
                TimeZone("america_maracaibo", "America/Caracas", "Maracaibo"),
                TimeZone("america_valparaiso", "America/Santiago", "Valparaíso"),

                // Europe
                TimeZone("europe_london", "Europe/London", "London"),
                TimeZone("europe_paris", "Europe/Paris", "Paris"),
                TimeZone("europe_berlin", "Europe/Berlin", "Berlin"),
                TimeZone("europe_rome", "Europe/Rome", "Rome"),
                TimeZone("europe_madrid", "Europe/Madrid", "Madrid"),
                TimeZone("europe_amsterdam", "Europe/Amsterdam", "Amsterdam"),
                TimeZone("europe_brussels", "Europe/Brussels", "Brussels"),
                TimeZone("europe_zurich", "Europe/Zurich", "Zurich"),
                TimeZone("europe_vienna", "Europe/Vienna", "Vienna"),
                TimeZone("europe_stockholm", "Europe/Stockholm", "Stockholm"),
                TimeZone("europe_oslo", "Europe/Oslo", "Oslo"),
                TimeZone("europe_copenhagen", "Europe/Copenhagen", "Copenhagen"),
                TimeZone("europe_helsinki", "Europe/Helsinki", "Helsinki"),
                TimeZone("europe_warsaw", "Europe/Warsaw", "Warsaw"),
                TimeZone("europe_prague", "Europe/Prague", "Prague"),
                TimeZone("europe_budapest", "Europe/Budapest", "Budapest"),
                TimeZone("europe_moscow", "Europe/Moscow", "Moscow"),
                TimeZone("europe_kiev", "Europe/Kiev", "Kyiv"),
                TimeZone("europe_bucharest", "Europe/Bucharest", "Bucharest"),
                TimeZone("europe_athens", "Europe/Athens", "Athens"),
                TimeZone("europe_istanbul", "Europe/Istanbul", "Istanbul"),
                TimeZone("europe_dublin", "Europe/Dublin", "Dublin"),
                TimeZone("europe_lisbon", "Europe/Lisbon", "Lisbon"),
                TimeZone("europe_milan", "Europe/Rome", "Milan"),
                TimeZone("europe_barcelona", "Europe/Madrid", "Barcelona"),
                TimeZone("europe_munich", "Europe/Berlin", "Munich"),
                TimeZone("europe_frankfurt", "Europe/Berlin", "Frankfurt"),
                TimeZone("europe_hamburg", "Europe/Berlin", "Hamburg"),
                TimeZone("europe_bratislava", "Europe/Bratislava", "Bratislava"),
                TimeZone("europe_ljubljana", "Europe/Ljubljana", "Ljubljana"),
                TimeZone("europe_zagreb", "Europe/Zagreb", "Zagreb"),
                TimeZone("europe_sofia", "Europe/Sofia", "Sofia"),
                TimeZone("europe_belgrade", "Europe/Belgrade", "Belgrade"),
                TimeZone("europe_sarajevo", "Europe/Sarajevo", "Sarajevo"),
                TimeZone("europe_skopje", "Europe/Skopje", "Skopje"),
                TimeZone("europe_tirana", "Europe/Tirana", "Tirana"),
                TimeZone("europe_riga", "Europe/Riga", "Riga"),
                TimeZone("europe_tallinn", "Europe/Tallinn", "Tallinn"),
                TimeZone("europe_vilnius", "Europe/Vilnius", "Vilnius"),
                TimeZone("europe_minsk", "Europe/Minsk", "Minsk"),

                // Asia
                TimeZone("asia_tokyo", "Asia/Tokyo", "Tokyo"),
                TimeZone("asia_shanghai", "Asia/Shanghai", "Shanghai"),
                TimeZone("asia_hong_kong", "Asia/Hong_Kong", "Hong Kong"),
                TimeZone("asia_singapore", "Asia/Singapore", "Singapore"),
                TimeZone("asia_seoul", "Asia/Seoul", "Seoul"),
                TimeZone("asia_taipei", "Asia/Taipei", "Taipei"),
                TimeZone("asia_bangkok", "Asia/Bangkok", "Bangkok"),
                TimeZone("asia_jakarta", "Asia/Jakarta", "Jakarta"),
                TimeZone("asia_kuala_lumpur", "Asia/Kuala_Lumpur", "Kuala Lumpur"),
                TimeZone("asia_manila", "Asia/Manila", "Manila"),
                TimeZone("asia_kolkata", "Asia/Kolkata", "Kolkata"),
                TimeZone("asia_delhi", "Asia/Kolkata", "New Delhi"),
                TimeZone("asia_mumbai", "Asia/Kolkata", "Mumbai"),
                TimeZone("asia_bangalore", "Asia/Kolkata", "Bangalore"),
                TimeZone("asia_chennai", "Asia/Kolkata", "Chennai"),
                TimeZone("asia_hyderabad", "Asia/Kolkata", "Hyderabad"),
                TimeZone("asia_karachi", "Asia/Karachi", "Karachi"),
                TimeZone("asia_lahore", "Asia/Karachi", "Lahore"),
                TimeZone("asia_islamabad", "Asia/Karachi", "Islamabad"),
                TimeZone("asia_dhaka", "Asia/Dhaka", "Dhaka"),
                TimeZone("asia_kathmandu", "Asia/Kathmandu", "Kathmandu"),
                TimeZone("asia_colombo", "Asia/Colombo", "Colombo"),
                TimeZone("asia_dubai", "Asia/Dubai", "Dubai"),
                TimeZone("asia_abu_dhabi", "Asia/Dubai", "Abu Dhabi"),
                TimeZone("asia_riyadh", "Asia/Riyadh", "Riyadh"),
                TimeZone("asia_jeddah", "Asia/Riyadh", "Jeddah"),
                TimeZone("asia_doha", "Asia/Qatar", "Doha"),
                TimeZone("asia_kuwait", "Asia/Kuwait", "Kuwait City"),
                TimeZone("asia_manama", "Asia/Bahrain", "Manama"),
                TimeZone("asia_muscat", "Asia/Muscat", "Muscat"),
                TimeZone("asia_tehran", "Asia/Tehran", "Tehran"),
                TimeZone("asia_baghdad", "Asia/Baghdad", "Baghdad"),
                TimeZone("asia_basra", "Asia/Baghdad", "Basra"),
                TimeZone("asia_kabul", "Asia/Kabul", "Kabul"),
                TimeZone("asia_tashkent", "Asia/Tashkent", "Tashkent"),
                TimeZone("asia_samarkand", "Asia/Tashkent", "Samarkand"),
                TimeZone("asia_almaty", "Asia/Almaty", "Almaty"),
                TimeZone("asia_astana", "Asia/Almaty", "Nur-Sultan"),
                TimeZone("asia_bishkek", "Asia/Bishkek", "Bishkek"),
                TimeZone("asia_dushanbe", "Asia/Dushanbe", "Dushanbe"),
                TimeZone("asia_ashgabat", "Asia/Ashgabat", "Ashgabat"),
                TimeZone("asia_yerevan", "Asia/Yerevan", "Yerevan"),
                TimeZone("asia_baku", "Asia/Baku", "Baku"),
                TimeZone("asia_tbilisi", "Asia/Tbilisi", "Tbilisi"),
                TimeZone("asia_yekaterinburg", "Asia/Yekaterinburg", "Yekaterinburg"),
                TimeZone("asia_novosibirsk", "Asia/Novosibirsk", "Novosibirsk"),
                TimeZone("asia_omsk", "Asia/Omsk", "Omsk"),
                TimeZone("asia_krasnoyarsk", "Asia/Krasnoyarsk", "Krasnoyarsk"),
                TimeZone("asia_irkutsk", "Asia/Irkutsk", "Irkutsk"),
                TimeZone("asia_vladivostok", "Asia/Vladivostok", "Vladivostok"),
                TimeZone("asia_khabarovsk", "Asia/Khabarovsk", "Khabarovsk"),
                TimeZone("asia_magadan", "Asia/Magadan", "Magadan"),
                TimeZone("asia_petropavlovsk", "Asia/Kamchatka", "Petropavlovsk-Kamchatsky"),
                TimeZone("asia_ulaanbaatar", "Asia/Ulaanbaatar", "Ulaanbaatar"),
                TimeZone("asia_pyongyang", "Asia/Pyongyang", "Pyongyang"),
                TimeZone("asia_ho_chi_minh", "Asia/Ho_Chi_Minh", "Ho Chi Minh City"),
                TimeZone("asia_hanoi", "Asia/Bangkok", "Hanoi"),
                TimeZone("asia_phnom_penh", "Asia/Phnom_Penh", "Phnom Penh"),
                TimeZone("asia_vientiane", "Asia/Vientiane", "Vientiane"),
                TimeZone("asia_yangon", "Asia/Yangon", "Yangon"),

                // Africa
                TimeZone("africa_cairo", "Africa/Cairo", "Cairo"),
                TimeZone("africa_johannesburg", "Africa/Johannesburg", "Johannesburg"),
                TimeZone("africa_cape_town", "Africa/Johannesburg", "Cape Town"),
                TimeZone("africa_lagos", "Africa/Lagos", "Lagos"),
                TimeZone("africa_nairobi", "Africa/Nairobi", "Nairobi"),
                TimeZone("africa_addis_ababa", "Africa/Addis_Ababa", "Addis Ababa"),
                TimeZone("africa_casablanca", "Africa/Casablanca", "Casablanca"),
                TimeZone("africa_tunis", "Africa/Tunis", "Tunis"),
                TimeZone("africa_algiers", "Africa/Algiers", "Algiers"),
                TimeZone("africa_accra", "Africa/Accra", "Accra"),
                TimeZone("africa_dakar", "Africa/Dakar", "Dakar"),
                TimeZone("africa_kinshasa", "Africa/Kinshasa", "Kinshasa"),
                TimeZone("africa_lubumbashi", "Africa/Lubumbashi", "Lubumbashi"),
                TimeZone("africa_dar_es_salaam", "Africa/Dar_es_Salaam", "Dar es Salaam"),
                TimeZone("africa_kampala", "Africa/Kampala", "Kampala"),
                TimeZone("africa_khartoum", "Africa/Khartoum", "Khartoum"),
                TimeZone("africa_maputo", "Africa/Maputo", "Maputo"),
                TimeZone("africa_harare", "Africa/Harare", "Harare"),
                TimeZone("africa_lusaka", "Africa/Lusaka", "Lusaka"),
                TimeZone("africa_gaborone", "Africa/Gaborone", "Gaborone"),
                TimeZone("africa_windhoek", "Africa/Windhoek", "Windhoek"),
                TimeZone("africa_porto_novo", "Africa/Porto-Novo", "Porto-Novo"),
                TimeZone("africa_lome", "Africa/Lome", "Lomé"),
                TimeZone("africa_ouagadougou", "Africa/Ouagadougou", "Ouagadougou"),
                TimeZone("africa_bamako", "Africa/Bamako", "Bamako"),
                TimeZone("africa_conakry", "Africa/Conakry", "Conakry"),
                TimeZone("africa_freetown", "Africa/Freetown", "Freetown"),
                TimeZone("africa_monrovia", "Africa/Monrovia", "Monrovia"),
                TimeZone("africa_abidjan", "Africa/Abidjan", "Abidjan"),
                TimeZone("africa_niamey", "Africa/Niamey", "Niamey"),
                TimeZone("africa_nouakchott", "Africa/Nouakchott", "Nouakchott"),
                TimeZone("africa_bangui", "Africa/Bangui", "Bangui"),
                TimeZone("africa_brazzaville", "Africa/Brazzaville", "Brazzaville"),
                TimeZone("africa_libreville", "Africa/Libreville", "Libreville"),
                TimeZone("africa_malabo", "Africa/Malabo", "Malabo"),
                TimeZone("africa_ndjamena", "Africa/Ndjamena", "N'Djamena"),
                TimeZone("africa_banjul", "Africa/Banjul", "Banjul"),
                TimeZone("africa_bissau", "Africa/Bissau", "Bissau"),
                TimeZone("africa_sao_tome", "Africa/Sao_Tome", "São Tomé"),
                TimeZone("africa_tripoli", "Africa/Tripoli", "Tripoli"),
                TimeZone("africa_rabat", "Africa/Casablanca", "Rabat"),
                TimeZone("africa_tangier", "Africa/Casablanca", "Tangier"),

                // Oceania
                TimeZone("australia_sydney", "Australia/Sydney", "Sydney"),
                TimeZone("australia_melbourne", "Australia/Melbourne", "Melbourne"),
                TimeZone("australia_brisbane", "Australia/Brisbane", "Brisbane"),
                TimeZone("australia_perth", "Australia/Perth", "Perth"),
                TimeZone("australia_adelaide", "Australia/Adelaide", "Adelaide"),
                TimeZone("australia_darwin", "Australia/Darwin", "Darwin"),
                TimeZone("australia_hobart", "Australia/Hobart", "Hobart"),
                TimeZone("australia_canberra", "Australia/Sydney", "Canberra"),
                TimeZone("pacific_auckland", "Pacific/Auckland", "Auckland"),
                TimeZone("pacific_wellington", "Pacific/Auckland", "Wellington"),
                TimeZone("pacific_fiji", "Pacific/Fiji", "Suva"),
                TimeZone("pacific_honolulu", "Pacific/Honolulu", "Honolulu"),
                TimeZone("pacific_tahiti", "Pacific/Tahiti", "Papeete"),
                TimeZone("pacific_samoa", "Pacific/Apia", "Apia"),
                TimeZone("pacific_tonga", "Pacific/Tongatapu", "Nuku'alofa"),
                TimeZone("pacific_port_moresby", "Pacific/Port_Moresby", "Port Moresby"),
                TimeZone("pacific_guam", "Pacific/Guam", "Hagåtña"),
                TimeZone("pacific_saipan", "Pacific/Saipan", "Saipan"),
                TimeZone("pacific_palau", "Pacific/Palau", "Ngerulmud"),
                TimeZone("pacific_majuro", "Pacific/Majuro", "Majuro"),
                TimeZone("pacific_tarawa", "Pacific/Tarawa", "Tarawa"),
                TimeZone("pacific_funafuti", "Pacific/Funafuti", "Funafuti"),
                TimeZone("pacific_pago_pago", "Pacific/Pago_Pago", "Pago Pago"),
                TimeZone("pacific_noumea", "Pacific/Noumea", "Nouméa"),
                TimeZone("pacific_port_vila", "Pacific/Efate", "Port Vila"),
                TimeZone("pacific_honiara", "Pacific/Guadalcanal", "Honiara"),

                // Atlantic
                TimeZone("atlantic_reykjavik", "Atlantic/Reykjavik", "Reykjavik"),
                TimeZone("atlantic_azores", "Atlantic/Azores", "Ponta Delgada"),
                TimeZone("atlantic_bermuda", "Atlantic/Bermuda", "Hamilton"),
                TimeZone("atlantic_canary", "Atlantic/Canary", "Las Palmas"),
                TimeZone("atlantic_madeira", "Atlantic/Madeira", "Funchal"),
                TimeZone("atlantic_st_helena", "Atlantic/St_Helena", "Jamestown"),
                TimeZone("atlantic_faroe", "Atlantic/Faroe", "Tórshavn"),
                TimeZone("atlantic_south_georgia", "Atlantic/South_Georgia", "Grytviken"),

                // Indian Ocean
                TimeZone("indian_mauritius", "Indian/Mauritius", "Port Louis"),
                TimeZone("indian_reunion", "Indian/Reunion", "Saint-Denis"),
                TimeZone("indian_maldives", "Indian/Maldives", "Malé"),
                TimeZone("indian_seychelles", "Indian/Seychelles", "Victoria"),
                TimeZone("indian_comoro", "Indian/Comoro", "Moroni"),
                TimeZone("indian_mayotte", "Indian/Mayotte", "Mamoudzou"),
                TimeZone("indian_antananarivo", "Indian/Antananarivo", "Antananarivo"),
                TimeZone("indian_kerguelen", "Indian/Kerguelen", "Port-aux-Français"),

                // Antarctica
                TimeZone("antarctica_mcmurdo", "Antarctica/McMurdo", "McMurdo Station"),
                TimeZone("antarctica_casey", "Antarctica/Casey", "Casey Station"),
                TimeZone("antarctica_davis", "Antarctica/Davis", "Davis Station"),
                TimeZone("antarctica_rothera", "Antarctica/Rothera", "Rothera Station"),
                TimeZone("antarctica_palmer", "Antarctica/Palmer", "Palmer Station")
            )
            insertTimeZones(defaultTimeZones)
        }
    }
}