package com.example.currency.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.example.currency.CurrencyService;

@Route("currency-exchange")
@PageTitle("Currency Exchange")
@Menu(order = 1, icon = "vaadin:money", title = "Currency Exchange")
public class CurrencyExchangeView extends Div {

    private final CurrencyService currencyService;

    public CurrencyExchangeView(CurrencyService currencyService) {
        this.currencyService = currencyService;

        ComboBox<String> fromCurrency = new ComboBox<>("From");
        ComboBox<String> toCurrency = new ComboBox<>("To");
        fromCurrency.setItems("EUR", "USD", "GBP");
        toCurrency.setItems("EUR", "USD", "GBP");

        NumberField amount = new NumberField("Amount");
        amount.setStep(0.01);
        amount.setMin(0.01);
        amount.setPlaceholder("Enter amount...");

        Button convertBtn = new Button("Convert", e -> {
            try {
                if (fromCurrency.isEmpty() || toCurrency.isEmpty() || amount.isEmpty()) {
                    Notification.show("Please fill all fields");
                    return;
                }
                double result = currencyService.convert(
                        fromCurrency.getValue(), toCurrency.getValue(), amount.getValue());
                Notification.show(amount.getValue() + " " + fromCurrency.getValue()
                        + " = " + String.format("%.2f", result) + " " + toCurrency.getValue());
            } catch (Exception ex) {
                Notification.show("Error: " + ex.getMessage());
            }
        });
        convertBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(fromCurrency, toCurrency, amount, convertBtn);
        setWidth("400px");
        getStyle().set("display", "flex");
        getStyle().set("flex-direction", "column");
        getStyle().set("gap", "var(--lumo-space-m)");
    }
}
