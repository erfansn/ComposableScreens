/*
 * Copyright 2024 Erfan Sn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ir.erfansn.composablescreens.food.kristina_cookie.feature.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ir.erfansn.composablescreens.food.kristina_cookie.data.CartStorage
import ir.erfansn.composablescreens.food.kristina_cookie.data.ProductProvider

internal class ProductViewModel(
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val productId = requireNotNull(savedStateHandle.get<Int>("id"))

  val product =
    ProductProvider.getProductList().first {
      it.id == productId
    }

  var quantity by mutableIntStateOf(CartStorage.storage.getOrDefault(productId, 0))
    private set

  fun changeProductQuantity(value: Int) {
    if (value == 0) {
      CartStorage.storage.remove(productId)
      quantity = 0
      return
    }

    CartStorage.storage[productId] = value
    quantity = CartStorage.storage[productId]
  }
}
