package com.jiujiu.task.ui.settings

import android.os.Bundle
import androidx.preference.*
import com.jiujiu.task.R
import com.jiujiu.task.util.AppConstant.PREF_KEY_OTHER_CATEGORY
import com.jiujiu.task.util.AppConstant.PREF_KEY_SECRET
import com.jiujiu.task.util.AppConstant.PREF_KEY_SECRET_OUTPUT
import com.jiujiu.task.util.AppConstant.PREF_KEY_SYNC_FREQUENCY

class SettingFragment : PreferenceFragmentCompat() {

    private var count = 0

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_settings, rootKey)
        setupPreference()
    }

    private fun setupPreference() {
        val otherCategory = findPreference<PreferenceCategory>(PREF_KEY_OTHER_CATEGORY)
        val secretOutput = findPreference<Preference>(PREF_KEY_SECRET_OUTPUT)

        otherCategory?.removePreference(secretOutput!!)

        findPreference<Preference>(PREF_KEY_SECRET)?.apply {
            setOnPreferenceClickListener {
                if (++count >= 7) {
                    otherCategory?.addPreference(secretOutput)
                }
                true
            }
        }

        findPreference<ListPreference>(PREF_KEY_SYNC_FREQUENCY)?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()

        findPreference<EditTextPreference>("example_text")?.summaryProvider = EditTextPreference.SimpleSummaryProvider.getInstance()
    }
}