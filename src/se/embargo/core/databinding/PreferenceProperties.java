package se.embargo.core.databinding;

import se.embargo.core.databinding.observable.AbstractObservableValue;
import se.embargo.core.databinding.observable.ChangeEvent;
import se.embargo.core.databinding.observable.ChangeEvent.ChangeType;
import se.embargo.core.databinding.observable.IObservableValue;
import android.content.SharedPreferences;

public class PreferenceProperties {
	public static IValueProperty<SharedPreferences, String> string(final String key, final String defvalue) {
		return new ValueProperty<SharedPreferences, String>() {
			public IObservableValue<String> observe(final SharedPreferences object) {
				return new PreferenceValue<String>(this, object, key);
			}

			@Override
			public String getValue(android.content.SharedPreferences object) {
				return object.getString(key, defvalue);
			}

			@Override
			public void setValue(android.content.SharedPreferences object, String value) {
				SharedPreferences.Editor editor = object.edit();
				editor.putString(key, value);
				editor.commit();
			}
		};
	}
	
	private static class PreferenceValue<ValueType> extends AbstractObservableValue<ValueType> implements SharedPreferences.OnSharedPreferenceChangeListener {
		private final IValueProperty<SharedPreferences, ValueType> _property;
		private final SharedPreferences _object;
		private final String _key;
		
		public PreferenceValue(IValueProperty<SharedPreferences, ValueType> property, SharedPreferences object, String key) {
			_property = property;
			_object = object;
			_key = key;
			_object.registerOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
			if (_key.equals(key)) {
				fireChangeEvent(new ChangeEvent<ValueType>(ChangeType.Reset, getValue()));
			}
		}

		@Override
		public ValueType getValue() {
			return _property.getValue(_object);
		}

		@Override
		public void setValue(ValueType value) {
			_property.setValue(_object, value);
		}
	}
}
